package com.workec.ectp.components;

import com.workec.ectp.dao.jpa.CallInterfaceDao;
import com.workec.ectp.dao.jpa.InterfaceDefDao;
import com.workec.ectp.dao.jpa.InterfaceParamDao;
import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/9.
 */

@Component
public class CaseComponent {

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private InterfaceParamDao interfaceParamDao;

/**
 * 组装用例信息：根据caseId获取用例数据
 */
public void initCase(Integer caseId){

    //获取前置步骤,location=1
    List<CallInterface> listBefore = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,1);

    //获取接口步骤,location=2
    List<CallInterface> listTest = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,2);

    //获取后置步骤,location=3
    List<CallInterface> listAfter = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,3);

    //组装前置接口调用的数据信息
    for (CallInterface callInterface:listBefore) {
        int interfaceId = callInterface.getInterfaceId();
        List<InterfaceParam> list = interfaceParamDao.findByInterfaceDefIdAndLocation(interfaceId,2);
        if(list!=null){
            int size = list.size();
            int formate = list.get(0).getFormat();
            String paramName = list.get(0).getParamName();

            if(formate==0 && paramName==null && size==1) {
                //组装Json格式的body
            }else if(formate==1 && paramName!=null){
                //组装Form格式的body
            }
        }else{
            //body为空
        }

        int stepId = callInterface.getId();
        initCallInterface(callInterface.getId());
    }

    //组装测试接口调用的数据信息
    for (CallInterface callInterface:listTest) {
        initCallInterface(callInterface.getId());
    }

    //组装后置接口调用的数据信息
    for (CallInterface callInterface:listAfter) {
        initCallInterface(callInterface.getId());
    }
}


/**
 * 组装步骤信息:根据callInterfaceId获取请求数据
 */
public void initCallInterface(Integer callInterfaceId){
    InterfaceDef interfaceDef;

    Integer method;
    String url;

    Map<String, Object> pathMap;

    Map<String, Object> headerMap;

    //form
    Map<String, Object> bodyMap;
    //json
    String bodyStr;

}

/**
 * 动态参数转换为值
*/
public String getTrueValue(){


    return null;
}



}
