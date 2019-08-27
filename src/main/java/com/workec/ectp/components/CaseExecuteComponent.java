package com.workec.ectp.components;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.dao.jpa.*;
import com.workec.ectp.entity.Bo.AssertResult;
import com.workec.ectp.entity.Bo.CallInterfaceResult;
import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.entity.Bo.InterfaceInitDataBackEnd;
import com.workec.ectp.entity.Do.*;
import com.workec.ectp.enums.CallInterfaceLocation;
import com.workec.ectp.enums.ExecuteCallsStatusEnum;
import com.workec.ectp.enums.InterfaceParamLocation;
import com.workec.ectp.enums.ReqDataMethod;
import com.workec.ectp.utils.ToolsUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.websocket.Encoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by user on 2018/3/21.
 */

@Component
public class CaseExecuteComponent {

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private MiddleParamDao middleParamDao;

    @Autowired
    private InterfaceDefDao interfaceDefDao;

    @Autowired
    private InterfaceParamDao interfaceParamDao;

    @Autowired
    private InterfaceComponent interfaceComponent;

    @Autowired
    private DataCacheComponent dataCacheComponent;

    @Autowired
    private CaseAssertDao caseAssertDao;

    @Autowired
    private CallInterfaceDataDao callInterfaceDataDao;

    @Autowired
    private HttpAPIComponent httpAPIComponent;

    @Autowired
    CaseExecuteComponent caseExecuteComponent;


    public  CaseExecuteResult caseExecuteOne(Integer userId,Integer appEnvId,Integer caseId){
        CaseExecuteThread caseExecuteThread = new CaseExecuteThread(caseExecuteComponent,userId,appEnvId,caseId);
        caseExecuteThread.start();

        try {
            caseExecuteThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return caseExecuteThread.getCaseExecuteResult();
    }



    /**
    * 执行用例
     *
    * */
    public  void caseExecute(Integer userId,Integer appEnvId,Integer caseId){

        //获取所有中间变量
        List<MiddleParam> params = middleParamDao.findByCaseId(caseId);
        Map<Integer,Object> map = new HashMap();
        for(MiddleParam param:params){
            map.put(param.getId(),"");
        }
        CasePublicParams.getThreadInstance().setMiddleParams(map);


        //初始化用例执行状态
        CaseExecuteResult.getThreadInstance().setCode(ExecuteCallsStatusEnum.SUCCESS.getCode());
        AssertResult assertResult = new AssertResult();
        CaseExecuteResult.getThreadInstance().setAssertResult(assertResult);


        //获取所有调用的接口
        List<CallInterface> pres = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId, CallInterfaceLocation.PREPOSITION.getCode());
        List<CallInterface> tests = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,CallInterfaceLocation.TEST.getCode());
        List<CallInterface> posts = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,CallInterfaceLocation.POSTPOSITION.getCode());


        //接收结果结果
        List<CallInterfaceResult> testResult = new ArrayList<>();
        List<CallInterfaceResult> preResults = null;
        List<CallInterfaceResult> postResults = null;


        //依次执行前置接口
        if(pres!=null&&pres.size()>0) {
            preResults= callsExecute("pres", userId, appEnvId, pres);
        }


        //执行准测试接口
        if(CaseExecuteResult.getThreadInstance().getCode()==0 && tests!=null&&tests.size()>0) {
            testResult = callsExecute("test",userId, appEnvId, tests);
            CaseExecuteResult.getThreadInstance().setTestResult(testResult);

            //断言
            int callInterfaceId = tests.get(0).getId();
            List<CaseAssert> caseAsserts = caseAssertDao.findByCallInterfaceId(callInterfaceId);
            for (CaseAssert caseAssert:caseAsserts) {
                assertExecute(preResults.get(0),caseAssert);
                if(CaseExecuteResult.getThreadInstance().getCode()== ExecuteCallsStatusEnum.ASSERT_FAILED.getCode())
                    break;
            }
        }


        //依次执行后置接口
        if(pres!=null && posts.size()>0) {
            postResults = callsExecute("posts", userId, appEnvId, posts);
        }

        //保存执行结果
        if(preResults!=null && preResults.size()>0) {
            testResult.addAll(0, preResults);
        }
        if(postResults!=null && postResults.size()>0) {
        testResult.addAll(postResults);
        }
        CaseExecuteResult.getThreadInstance().setTestResult(testResult);

    }



    /**
     * 执行步骤，并获取执行结果
     *
     * */
    private List<CallInterfaceResult> callsExecute(String part,Integer userId,Integer appEnvId,List<CallInterface> calls){
        //逐步执行+断言结果+提取参数+更新中间变量+返回结果
        Boolean status = true;

        List<CallInterfaceResult> results = new ArrayList<>();
        for(CallInterface callInterface:calls){
            int callId = callInterface.getId();
            //初始化接口调用信息
            InterfaceInitDataBackEnd initData = initCallInterface(callInterface,userId,appEnvId);

            //调用接口
            CallInterfaceResult callResult = getCallInterfaceResult(initData);

            //设置【步骤】执行状态
            callResult.setExecuteStatus(ExecuteCallsStatusEnum.SUCCESS.getCode());
            callResult.setExecuteMsg(ExecuteCallsStatusEnum.SUCCESS.getMessage());

            //断言

            //提取变量
            List<MiddleParam> middleParams = middleParamDao.findByCallInterfaceId(callId);
            for (MiddleParam middleParam:middleParams) {
                int middleParamId = middleParam.getId();
                if(middleParam.getType()==1){       //正则提取
                    String paramValue = getMiddleParamRegxValue(middleParam,callResult);
                    if(paramValue!=null) {
                        //提取成功，更新到缓存中去
                        Map<String, String> keyValue = new HashMap<>();
                        keyValue.put(middleParam.getName(), paramValue);
                        CasePublicParams.getThreadInstance().getMiddleParams().put(middleParamId, keyValue);
                    }else {
                        String message = "中间变量提取失败,"
                                + (middleParam.getRspResource()==1?"从主请求的":"")
                                + (middleParam.getRspLocation()==1?"header":middleParam.getRspLocation()==2?"body":"" )
                                +"中,按照正则表达式:  "+ middleParam.getExpression() +"  提取第"
                                + middleParam.getTemplateNum()+"个模板,第"
                                + middleParam.getValueNum()+"个值,结果为空";

                        //提取失败，更新执行状态
                        AssertResult assertResult = new AssertResult();
                        assertResult.setId(middleParamId);    //返回中间变量Id
                        assertResult.setExpression(
                                (callInterface.getLocation()==1?"前置":callInterface.getLocation()==2?"测试":callInterface.getLocation()==1?"后置":"")
                                +"第"+ callInterface.getStep() + "步，" + callInterface.getLabel() +"," + message);    //返回提取失败的原因

                        //更新【步骤】执行状态
                        callResult.setExecuteStatus(ExecuteCallsStatusEnum.MIDDLE_PARAM_GET_FAILED.getCode());
                        callResult.setExecuteMsg(message);

                        //更新【用例】执行状态
                        CaseExecuteResult.getThreadInstance().setAssertResult(assertResult);
                        CaseExecuteResult.getThreadInstance().setCode(ExecuteCallsStatusEnum.MIDDLE_PARAM_GET_FAILED.getCode());

                        //后置步骤不断开，全部执行
                        if(!part.equals("posts")) {
                            status = false;
                            break;
                        }
                    }
                }else{
                    //其它提取方式，如：JSONPath
                }
            }

            //保存结果
            results.add(callResult);
            if(!status) break;
        }
        return results;
    }




    /**
     * 正则提取中间变量
     */
    public String getMiddleParamRegxValue(MiddleParam middleParam,CallInterfaceResult callInterfaceResult){
        String textToSearch = null;
        String matchExpression;
        Pattern pattern;
        Matcher matchResult;
        String paramValue = null;

        int templateNum = middleParam.getTemplateNum()==null?0:middleParam.getTemplateNum();
        int valueNum = middleParam.getValueNum()==null?0:middleParam.getValueNum();
        int rspResource = middleParam.getRspResource()==null?0:middleParam.getRspResource();
        int rspLocation = middleParam.getRspLocation()==null?0:middleParam.getRspLocation();


        if (rspResource== 1) {  //1数据来源主请求结果
            if (rspLocation == 1) {  //1断言结果的header信息
                Map headersMap = callInterfaceResult.getResult().getHeaders();
                textToSearch = ToolsUtil.mapToHeader(headersMap).toString();
            } else if (rspLocation == 2) {  //2断言结果的body信息
                Object bodyObj = callInterfaceResult.getResult().getBody();

                if(bodyObj!=null && bodyObj instanceof Map) {   //Json格式的body
                    Map bodyMap = (Map)bodyObj;
                    textToSearch = JSONUtils.toJSONString(bodyMap);
                }else if(bodyObj!=null && bodyObj instanceof String) {   //HTML格式的body
                    textToSearch = bodyObj.toString();
                }
            } else {
                //其它部分
            }

            matchExpression = middleParam.getExpression();
            pattern = Pattern.compile(matchExpression);
            matchResult = pattern.matcher(textToSearch);



            //用户指定的模板编号 <= 正则表达式中模板数量
            if(matchResult!=null && matchResult.groupCount()<=templateNum) {
                int count = 1;
                while (matchResult.find()) {
                    if (valueNum == count++) {
                        paramValue = matchResult.group(templateNum);
                        break;
                    }
                }
            }
        }
        return paramValue;
    }




    /**
     * 执行断言，并更新断言结果
     *
     * */
    private AssertResult assertExecute(CallInterfaceResult callInterfaceResult, CaseAssert caseAssert){
        AssertResult assertResult = new AssertResult();

        if(caseAssert.getType()==1) {    //1响应断言

            String textToSearch = null;
            if (caseAssert.getRspResource() == 1) {  //1数据来源主请求结果
                if(caseAssert.getRspLocation()==1){  //1断言结果的header信息
                    if(caseAssert.getRule()==1){    //1包含
                        Map headersMap = callInterfaceResult.getResult().getHeaders();
                        textToSearch = ToolsUtil.mapToHeader(headersMap).toString();
                    }
                }else if(caseAssert.getRspLocation()==2){  //2断言结果的body信息
                    if(caseAssert.getRule()==1){    //1包含
                        textToSearch = callInterfaceResult.getResult().getBody().toString();
                    }
                }else{
                    //其它部分
                }

            }else{
                //其它数据来源：主请求&&子请求、子请求
            }

            String substring = caseAssert.getExpression();
            if(textToSearch.contains(substring)){
                CaseExecuteResult.getThreadInstance().setCode(ExecuteCallsStatusEnum.SUCCESS.getCode());
            }else{
                //更新执行状态和执行结果
                CaseExecuteResult.getThreadInstance().setCode(ExecuteCallsStatusEnum.ASSERT_FAILED.getCode());
                assertResult.setId(caseAssert.getId());     //断言Id
                assertResult.setExpression(                 //失败原因
                        "断言失败,希望在"
                        + (caseAssert.getRspResource()==1?"主请求的":"")
                        + (caseAssert.getRspLocation()==1?"header":caseAssert.getRspLocation()==2?"body":"" )
                        +"中，"
                        + (caseAssert.getRule()==1?"包含:":"")
                        + ">>>   " + caseAssert.getExpression() + "   <<<"
                );
                CaseExecuteResult.getThreadInstance().setAssertResult(assertResult);
            }


        }else{
            //其它断言方式：json解析、HTML解析、XML解析等
        }

        return assertResult;
    }




    /**
     * 组装步骤信息:根据InterfaceId获取请求数据
     */
    public InterfaceInitDataBackEnd initCallInterface(CallInterface callInterface,Integer userId,Integer appEnvId){
        InterfaceInitDataBackEnd initData = new InterfaceInitDataBackEnd();

        Integer reqMethod;
        String url;
        Object body = null;
        Map<String,Object> headerMap;
        Map<String,Object> pathMap;

        //调用信息提取
        int step = callInterface.getStep();
        String label = callInterface.getLabel();
        int location = callInterface.getLocation();

        int callInterfaceId = callInterface.getId();
        int interfaceId = callInterface.getInterfaceId();
        if(!interfaceDefDao.exists(interfaceId)){
            return initData;
        }

        //获取接口信息
        InterfaceDef interfaceDef = interfaceDefDao.findOne(interfaceId);

        //组装url
        url = interfaceComponent.getUrl(interfaceDef,appEnvId);


        //请求头
        List<InterfaceParam> headerList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId, InterfaceParamLocation.HEADER.getCode());
        headerMap = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,headerList,userId,appEnvId);


        //url参数
        List<InterfaceParam> pathList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId,InterfaceParamLocation.PATH.getCode());
        pathMap = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,pathList,userId,appEnvId);


        //请求方法
        reqMethod = interfaceDef.getReqMethod();
        if(reqMethod==2){
            //获取接口参数
            List<InterfaceParam> bodyList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId, InterfaceParamLocation.BODY.getCode());
            if(bodyList!=null&&bodyList.size()>0){
                int size = bodyList.size();
                InterfaceParam firstParam = bodyList.get(0);
                int formate = firstParam.getFormat();
                String paramName = firstParam.getParamName();
                int paramId = firstParam.getId();

                if(formate==0  && size==1 && (paramName==null||paramName.equals(""))) {
                    //请求方法
                    reqMethod = ReqDataMethod.POST_JSON.getCode();

                    //组装Json格式的body
                    CallInterfaceData callInterfaceData = callInterfaceDataDao.findByCallInterfaceIdAndParamKeyId(callInterfaceId,paramId);
                    body = callInterfaceData.getParamsValue();
                    /** 动态参数转换+++ **/
                    body = variableToValue(String.valueOf(body),userId,appEnvId);

             /*     String bodyStr = variableToValue(String.valueOf(body),userId,appEnvId);
                    try {
                        JSONObject bodyJsonObj = new JSONObject(bodyStr);
                        System.out.println(">>>>>>>>>>>>>>>>>>bodyJsonObj:"+bodyJsonObj);
                        Map bodyMap = ToolsUtil.transferMap(bodyJsonObj);
                        System.out.println(">>>>>>>>>>>>>>>>>>bodyMap:"+bodyMap);
                        body = bodyMap;
                        System.out.println(">>>>>>>>>>>>>>>>>>body:"+body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                }else if(formate==1 && paramName!=null){

                    //请求方法
                    reqMethod = ReqDataMethod.POST_FORM.getCode();
                    //组装Form格式的body
                    body = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,bodyList,userId,appEnvId);
                }
            }

        }else if(reqMethod==1){
            reqMethod = ReqDataMethod.REQUEST_GET.getCode();
        }

        //返回结果
        initData.setCallInterfaceId(callInterfaceId);
        initData.setReqMethod(reqMethod);
        initData.setUrl(url);
        initData.setHeader(headerMap);
        initData.setPath(pathMap);
        initData.setBody(body);
        initData.setLocation(location);
        initData.setStep(step);
        initData.setLabel(label);
        return initData;
    }




    /**
     * 执行接口调用
     *
     * */
    public CallInterfaceResult getCallInterfaceResult(InterfaceInitDataBackEnd initData){

            int location = initData.getLocation()!=null?initData.getLocation():0;
            int step = initData.getStep()!=null?initData.getLocation():0;
            String label = initData.getLabel();

            int callInterfaceId = initData.getCallInterfaceId();
            int reqMethod = initData.getReqMethod();
            String url = initData.getUrl();
            Map<String,Object> headerMap = initData.getHeader();
            Map<String,Object> pathMap = initData.getPath();
            Object body = initData.getBody();

            HttpResult httpResult = new HttpResult();
            if(reqMethod==ReqDataMethod.REQUEST_GET.getCode()) {
                httpResult = httpAPIComponent.doGet(url,pathMap,headerMap);
            }else if(reqMethod==ReqDataMethod.POST_FORM.getCode()) {
                Map<String,Object> bodyMap = (Map<String,Object>)body;
                httpResult = httpAPIComponent.doPostForm(url,pathMap,headerMap,bodyMap);
            }else if(reqMethod==ReqDataMethod.POST_JSON.getCode()) {
                String bodyStr = body!=null?body.toString():"";
                httpResult = httpAPIComponent.doPostJson(url,pathMap,headerMap,bodyStr);
            }

            CallInterfaceResult callInterfaceResult = new CallInterfaceResult();
            callInterfaceResult.setLocation(location);
            callInterfaceResult.setStep(step);
            callInterfaceResult.setLabel(label);
            callInterfaceResult.setCallInterfaceId(callInterfaceId);
            callInterfaceResult.setReqInfo(initData);
            callInterfaceResult.setResult(httpResult);

        return callInterfaceResult;
    }




    /**
     * 动态参数转换为值
     */
    public Map<String,Object> getKeyValuePairByParamListAndCallInterfaceId(Integer callInterfaceId,List<InterfaceParam> paramList,Integer userId,Integer appEnvId){

        Map<String, Object> map = new HashMap();
        for(InterfaceParam interfaceParam: paramList){
            String paramName = interfaceParam.getParamName();
            int paramId = interfaceParam.getId();
            String paramValue = null;
            CallInterfaceData callInterfaceData = callInterfaceDataDao.findByCallInterfaceIdAndParamKeyId(callInterfaceId,paramId);

            if(callInterfaceData!=null && !callInterfaceData.equals("")){
                paramValue = callInterfaceData.getParamsValue();
                /** 动态参数转换+++ **/
                paramValue = variableToValue(paramValue,userId,appEnvId);
            }
            map.put(paramName,paramValue);
        }
        return map;
    }


    public String variableToValue(String paramValue,Integer userId,Integer appEnvId){
        //1、设置匹配规则
        String regexRegular =  "(全局变量|中间变量|公共方法)-(.*?)-\\$\\{(.*?)::(.*?)}";
        //        String regexRegular =  "^(全局变量|中间变量|公共方法)-(.*?)-\\$\\{(.*?)::(.*?)}$";

        //3、匹配
        Pattern pattern = Pattern.compile(regexRegular);
        Matcher matchResult = pattern.matcher(paramValue);
        StringBuffer sb = new StringBuffer();
        int strStart = 0;
        while (matchResult.find()) {
            int type = Integer.valueOf(matchResult.group(3));
            int paramId = Integer.valueOf(matchResult.group(4));
            int start = matchResult.start();
            int end = matchResult.end();
            sb.append(paramValue.substring(strStart, start));

            if(type==1){    //全局参数
                String value =dataCacheComponent.getParamValueByUserIdAndEnvIdAndParamId(userId,appEnvId,paramId);
                sb.append(value);

            }else if(type==2){    //公共方法
                sb.append(matchResult.group());     //未实现，保持原样不做替换
            }else if(type==3){    //中间变量.正则
                Object keyValueObj = CasePublicParams.getThreadInstance().getMiddleParams().get(paramId);
                if(keyValueObj!=null&&!keyValueObj.equals("")) {
                    Map<Integer, Object> map = (Map<Integer, Object>) CasePublicParams.getThreadInstance().getMiddleParams().get(paramId);
                    sb.append(map.values().iterator().next());
                }else {
                    sb.append("");
                }
            }else if(type==4){    //中间变量. JSON
                sb.append(matchResult.group());     //未实现，保持原样不做替换
            }else {
                sb.append(matchResult.group());     //未能匹配到，保持原样不做替换
            }
            strStart = end;
        }
        if ( strStart < paramValue.length() ) {
            sb.append(paramValue.substring(strStart));
        }

        return sb.toString();
    }



}





/**
 * 中间变量
 *
 * */
class CasePublicParams{
    private CasePublicParams(){}               //构造方法私有化
    public static  CasePublicParams getThreadInstance(){
        CasePublicParams instance = map.get();     //通过map来判断有没有其他线程生成实例对象，如果没有就创建，所以不需要加入synchronized
        if(instance == null){
            instance = new CasePublicParams();
            map.set(instance);
        }
        return instance;
    }

    private static ThreadLocal<CasePublicParams> map = new ThreadLocal<CasePublicParams>();//把ThreadLocal封装在一个类的内部

    private Map<Integer,Object> middleParams;



    public Map<Integer, Object> getMiddleParams() {
        return middleParams;
    }
    public void setMiddleParams(Map<Integer, Object> middleParams) {
        this.middleParams = middleParams;
    }
}



/**
 * 执行线程
 *
 * */
class CaseExecuteThread extends Thread{

    private CaseExecuteComponent caseExecuteComponent;
    private Integer userId;
    private Integer appEnvId;
    private Integer caseId;
    private CaseExecuteResult caseExecuteResult;

    public CaseExecuteThread(CaseExecuteComponent caseExecuteComponent,Integer userId,Integer appEnvId,Integer caseId){
        this.userId = userId;
        this.appEnvId = appEnvId;
        this.caseId = caseId;
        this.caseExecuteComponent = caseExecuteComponent;
    }

    @Override
    public void run() {
        caseExecuteComponent.caseExecute(userId,appEnvId,caseId);
        setCaseExecuteResult(CaseExecuteResult.getThreadInstance());
    }

    public CaseExecuteResult getCaseExecuteResult() {
        return caseExecuteResult;
    }

    public void setCaseExecuteResult(CaseExecuteResult caseExecuteResult) {
        this.caseExecuteResult = caseExecuteResult;
    }
}

/**
 * 执行结果
 *
 * */
@JsonInclude(JsonInclude.Include.ALWAYS)
class CaseExecuteResult{
    private CaseExecuteResult(){}               //构造方法私有化
    public static  CaseExecuteResult getThreadInstance(){
        CaseExecuteResult instance = map.get();     //通过map来判断有没有其他线程生成实例对象，如果没有就创建，所以不需要加入synchronized
        if(instance == null){
            instance = new CaseExecuteResult();
            map.set(instance);
        }
        return instance;
    }

    private static ThreadLocal<CaseExecuteResult> map = new ThreadLocal<CaseExecuteResult>();//把ThreadLocal封装在一个类的内部

    private Integer code;
    private AssertResult assertResult;

    //调整展示放到一个list里面，不区分前后置
    /*private List<CallInterfaceResult> preResults;
    private List<CallInterfaceResult> testResult;
    private List<CallInterfaceResult> postResults;*/
    private List<CallInterfaceResult> testResult;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public AssertResult getAssertResult() {
        return assertResult;
    }

    public void setAssertResult(AssertResult assertResult) {
        this.assertResult = assertResult;
    }

    public List<CallInterfaceResult> getTestResult() {
        return testResult;
    }

    public void setTestResult(List<CallInterfaceResult> testResult) {
        this.testResult = testResult;
    }

}