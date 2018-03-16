package com.workec.ectp.components;

import com.workec.ectp.dao.jdbc.Impl.InterfaceParamDaoImpl;
import com.workec.ectp.dao.jpa.*;
import com.workec.ectp.entity.Bo.*;
import com.workec.ectp.entity.Do.*;
import com.workec.ectp.enums.CallInterfaceLocation;
import com.workec.ectp.enums.InterfaceParamLocation;
import com.workec.ectp.enums.ReqDataMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/9.
 */

@Component
public class CaseComponent {

    @Autowired
    CaseDao caseDao;

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private InterfaceDefDao interfaceDefDao;

    @Autowired
    private InterfaceParamDao interfaceParamDao;

    @Autowired
    private InterfaceParamDaoImpl interfaceParamDaoImpl;

    @Autowired
    private DomainDao domainDao;

    @Autowired
    private CallInterfaceDataDao callInterfaceDataDao;

    @Autowired
    private MiddleParamDao middleParamDao;

    @Autowired
    private HttpAPIComponent httpAPIComponent;

    @Autowired
    private InterfaceComponent interfaceComponent;

/**
* 执行用例：根据caseId执行用例，并获取执行结果
*/
public CaseExecuteResult executeOneCase(Integer caseId,Integer applicationEnvironmentId,Integer executeUserId){

    //初始化用例数据
    Map map = initCase(caseId);
    List<InterfaceInitDataBackEnd> preCallsDataList= (List<InterfaceInitDataBackEnd>)map.get("beforeCallsDataList");
    List<InterfaceInitDataBackEnd> testCallsData = (List<InterfaceInitDataBackEnd>)map.get("testCallsDataList");
    List<InterfaceInitDataBackEnd> postCallsDataList =  (List<InterfaceInitDataBackEnd>)map.get("afterCallsDataList");

    //获取每一步执行及结果数据
    List<CallInterfaceResult> preResults = getCallInterfaceResultList(preCallsDataList);
    List<CallInterfaceResult> testResult = getCallInterfaceResultList(testCallsData);
    List<CallInterfaceResult> postResults = getCallInterfaceResultList(postCallsDataList);

    //返回执行结果
    CaseExecuteResult caseExecuteResult = new CaseExecuteResult();
    caseExecuteResult.setPreResults(preResults);
    caseExecuteResult.setTestResult(testResult);
    caseExecuteResult.setPostResults(postResults);

    return caseExecuteResult;
}



private List<CallInterfaceResult> getCallInterfaceResultList(List<InterfaceInitDataBackEnd> callInterfaceDataList){

    List<CallInterfaceResult> list = new ArrayList<>();

    for(InterfaceInitDataBackEnd initData:callInterfaceDataList){
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
        callInterfaceResult.setCallInterfaceId(callInterfaceId);
        callInterfaceResult.setReqInfo(initData);
        callInterfaceResult.setResult(httpResult);
        list.add(callInterfaceResult);
    }

    //返回callId、HttpResult
    return list;
}




 /**
 * 后端组装用例信息：根据caseId获取用例数据
 */
public Map initCase(Integer caseId){

    //获取前置调用的接口,location=1
    List<CallInterface> beforeCalls = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId, CallInterfaceLocation.PREPOSITION.getCode());

    //获取测试接口,location=2
    List<CallInterface> testCalls = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,CallInterfaceLocation.TEST.getCode());

    //获取后置调用的接口,location=3
    List<CallInterface> afterCalls = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,CallInterfaceLocation.POSTPOSITION.getCode());

    //组装前置接口调用的数据信息
    List<InterfaceInitDataBackEnd> beforeCallsDataList = initCallInterfaceList(beforeCalls);

    //组装测试接口调用的数据信息
    List<InterfaceInitDataBackEnd> testCallsDataList = initCallInterfaceList(testCalls);

    //组装后置接口调用的数据信息
    List<InterfaceInitDataBackEnd> afterCallsDataList = initCallInterfaceList(afterCalls);

    Map map = new HashMap();
    map.put("beforeCallsDataList",beforeCallsDataList);
    map.put("testCallsDataList",testCallsDataList);
    map.put("afterCallsDataList",afterCallsDataList);
    return map;
}

//把用例步骤列表 转换为 可执行的用例数据列表
private List<InterfaceInitDataBackEnd> initCallInterfaceList(List<CallInterface> list){
    List<InterfaceInitDataBackEnd> resultList = new ArrayList<>();
    //逐个用例组装
    for (CallInterface callInterface:list) {
        InterfaceInitDataBackEnd initData = initCallInterface(callInterface);
        resultList.add(initData);
    }
    return resultList;
}




/**
 * 组装步骤信息:根据InterfaceId获取请求数据
 */
public InterfaceInitDataBackEnd initCallInterface(CallInterface callInterface){

    Integer reqMethod;
    String url;
    Object body = null;
    Map<String,Object> headerMap;
    Map<String,Object> pathMap;

    //调用信息提取
    int callInterfaceId = callInterface.getId();
    int interfaceId = callInterface.getInterfaceId();


    //获取接口信息
    InterfaceDef interfaceDef = interfaceDefDao.findOne(interfaceId);

    //组装url
    url = interfaceComponent.getUrl(interfaceDef);


    //请求头
    List<InterfaceParam> headerList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId,InterfaceParamLocation.HEADER.getCode());
    headerMap = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,headerList);



    //url参数
    List<InterfaceParam> pathList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId,InterfaceParamLocation.PATH.getCode());
    pathMap = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,pathList);



    //请求方法
    reqMethod = interfaceDef.getReqMethod();
    if(reqMethod==2){
        //获取接口参数
        List<InterfaceParam> bodyList = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId, InterfaceParamLocation.BODY.getCode());
        if(bodyList!=null){
            int size = bodyList.size();
            InterfaceParam firstParam = bodyList.get(0);
            int formate = firstParam.getFormat();
            String paramName = firstParam.getParamName();
            int paramId = firstParam.getId();

            if(formate==0 && paramName==null && size==1) {

                //请求方法
                reqMethod = ReqDataMethod.POST_JSON.getCode();

                //组装Json格式的body
                CallInterfaceData callInterfaceData = callInterfaceDataDao.findByCallInterfaceIdAndParamKeyId(callInterfaceId,paramId);
                body = callInterfaceData.getParamsValue();
                /** 动态参数转换+++ **/


            }else if(formate==1 && paramName!=null){

                //请求方法
                reqMethod = ReqDataMethod.POST_FORM.getCode();
                //组装Form格式的body
                body = getKeyValuePairByParamListAndCallInterfaceId(callInterfaceId,bodyList);
            }
        }

    }else if(reqMethod==1){
        reqMethod = ReqDataMethod.REQUEST_GET.getCode();
    }

    //返回结果
    InterfaceInitDataBackEnd initData = new InterfaceInitDataBackEnd();
    initData.setCallInterfaceId(callInterfaceId);
    initData.setReqMethod(reqMethod);
    initData.setUrl(url);
    initData.setHeader(headerMap);
    initData.setPath(pathMap);
    initData.setBody(body);
    return initData;
}




/**
 * 动态参数转换为值
*/
private Map<String,Object> getKeyValuePairByParamListAndCallInterfaceId(Integer callInterfaceId,List<InterfaceParam> paramList){

    Map<String, Object> map = new HashMap();
    for(InterfaceParam interfaceParam: paramList){
        String paramName = interfaceParam.getParamName();
        int paramId = interfaceParam.getId();
        String value = null;
        CallInterfaceData callInterfaceData = callInterfaceDataDao.findByCallInterfaceIdAndParamKeyId(callInterfaceId,paramId);
        if(callInterfaceData!=null && !callInterfaceData.equals("")){
            value = callInterfaceData.getParamsValue();
            /** 动态参数转换+++ **/


        }
        map.put(paramName,value);
    }
    return map;
}

/**
 * 获取调用信息
 */
    public CallInterfaceInfo getCallInterfaceInfo(Integer callInterfaceId){

        CallInterface callInterface = callInterfaceDao.findOne(callInterfaceId);
        int interfaceId = callInterface.getInterfaceId();

        //组装接口信息
        InterfaceInitDataFrontEnd interfaceStructure = interfaceComponent.getInterfaceStructure(callInterfaceId,interfaceId);

        //中间参数
        List<MiddleParam> middleParams = middleParamDao.findByCallInterfaceId(callInterfaceId);

        //组装结果
        CallInterfaceInfo result = new CallInterfaceInfo();
        result.setCallInterfaceId(callInterfaceId);
        result.setInterfaceId(interfaceId);
        result.setInterfaceName(interfaceStructure.getInterfaceName());
        result.setReqMethod(interfaceStructure.getReqMethod());
        result.setAccessAddress(interfaceStructure.getAccessAddress());
        result.setHeader(interfaceStructure.getHeader());
        result.setPath(interfaceStructure.getPath());
        result.setBody(interfaceStructure.getBody());
        result.setMiddleParam(middleParams);

        return result;
    }

    public Boolean existsCallInterfaceId(Integer interfaceId){
        return callInterfaceDao.exists(interfaceId);
    }



    /**
     * 删除用例
     * */
    @Transactional
    public Boolean deleteCaseById(Integer id) {

        //获取用例步骤
        if(callInterfaceDao.getIdByCaseId(id) != null) {
            List<Integer> steplist = callInterfaceDao.getIdByCaseId(id);

            //删除步骤和数据
            for (int i = 0; i < steplist.size(); i++) {
                List<Integer> paramslist = callInterfaceDataDao.getIdByCallInterfaceId(steplist.get(i));
                for (int j = 0; j < paramslist.size(); j++) {
                    callInterfaceDataDao.delete(paramslist.get(j));
                }
                callInterfaceDao.delete(steplist.get(i));
            }
        }

        caseDao.delete(id);

        if(caseDao.exists(id)) {
            return true;
        }else {
            return false;
        }
    }



    /**
     * 保存步骤
     * */
    @Transactional
    public CallInterfaceInfo saveStep(CallInterfaceDataSave cifds) {

        //保存步骤
        CallInterface callInterface = cifds.getCallInterface();
        //a.如果已存在准测试接口的步骤数据，不再新增
        if(callInterface.getId()==0&&callInterface.getLocation()==CallInterfaceLocation.TEST.getCode()){
            List<CallInterface> tests = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(callInterface.getCaseId(),callInterface.getLocation());
            if(tests!=null&&tests.size()>0){
                return null;
            }
        }
        //b.新增、修改
        CallInterface callInterfaceReslult = callInterfaceDao.save(callInterface);


        //保存参数值
        int callInterfaceId =callInterfaceReslult.getId();
        List<CallInterfaceData> paramlist =cifds.getParamData();
        for(int i =0;i< paramlist.size();i++){
            CallInterfaceData callInterfaceData = paramlist.get(i);
            callInterfaceData.setCallInterfaceId(callInterfaceId);
            callInterfaceDataDao.save(callInterfaceData);
        }

        //保存中间变量
        List<MiddleParam> middleParamList = cifds.getMiddleParams();
        if(middleParamList!=null&&middleParamList.size()>0){
            for (int j =0;j <middleParamList.size();j++){
                middleParamDao.save(middleParamList.get(j));
            }
        }

        this.getCallInterfaceInfo(callInterfaceId);
        CallInterfaceInfo callInterfaceInfo = this.getCallInterfaceInfo(callInterfaceId);

        return callInterfaceInfo;
    }


    /**
     * 删除步骤
     * */
    public void deleteStep(Integer id) {

        List<Integer> paramlist = callInterfaceDataDao.getIdByCallInterfaceId(id);
        for(int i = 0;i< paramlist.size();i++){
            callInterfaceDataDao.delete(paramlist.get(i));
        }
        callInterfaceDao.delete(id);
    }


    public Case getCase(Integer caseId){
        return caseDao.findOne(caseId);
    }
}
