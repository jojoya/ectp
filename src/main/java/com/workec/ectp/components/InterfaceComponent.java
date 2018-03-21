package com.workec.ectp.components;

import com.workec.ectp.dao.jdbc.Impl.InterfaceParamDaoImpl;
import com.workec.ectp.dao.jpa.DomainDao;
import com.workec.ectp.dao.jpa.InterfaceDefDao;
import com.workec.ectp.dao.jpa.InterfaceParamDao;
import com.workec.ectp.entity.Bo.InterfaceInitDataFrontEnd;
import com.workec.ectp.entity.Bo.InterfaceParamForCallInfo;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
import com.workec.ectp.enums.InterfaceParamLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Component
public class InterfaceComponent {

    @Autowired
    private InterfaceDefDao interfaceDefDao;

    @Autowired
    private InterfaceParamDao interfaceParamDao;

    @Autowired
    private InterfaceParamDaoImpl interfaceParamDaoImpl;

    @Autowired
    private DomainDao domainDao;

    /* 定义接口-InterfaceDef */
    public InterfaceDef saveDef(@Valid InterfaceDef interfaceDef, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return interfaceDef;
        }

        //设置moduleId为接口当前moduleId
        interfaceDef.setModuleId(interfaceDefDao.getOne(interfaceDef.getId()).getModuleId());

        return interfaceDefDao.save(interfaceDef);
    }

    /* 定义接口参数-InterfaceParam */
    public InterfaceParam  saveParam(@Valid InterfaceParam interfaceParam, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return interfaceParam;
        }

        return interfaceParamDao.save(interfaceParam);
    }




    /**
     * 查询接口的结构，如果有被调用就从调用信息里面取值（callInterfaceId=null），如果没有被调用就从定义里面取值（callInterfaceId!=null）
     * */
    public InterfaceInitDataFrontEnd getInterfaceStructure(Integer callInterfaceId, Integer interfaceId){
        InterfaceInitDataFrontEnd dataFrontEnd = new InterfaceInitDataFrontEnd();
        if(!interfaceDefDao.exists(interfaceId)){
            return dataFrontEnd;
        }

        //获取def
        InterfaceDef resultDef = interfaceDefDao.findOne(interfaceId);

        //请求方法
        String reqMethod = "";
        if(resultDef.getReqMethod()==1){
            reqMethod = "GET";
        }else if(resultDef.getReqMethod()==2){
            reqMethod = "POST";
        }

        String interfaceName = resultDef.getLabel();

        //访问地址
        String accessAddress = this.getUrl(resultDef);

        callInterfaceId=callInterfaceId==null?0:callInterfaceId;

        //获取params，如果有被调用就从调用信息里面取值（callInterfaceId=null），如果没有被调用就从定义里面取值（callInterfaceId!=null）
        List<InterfaceParamForCallInfo> header = null;
        List<InterfaceParamForCallInfo> path = null;
        List<InterfaceParamForCallInfo> body = null;
        if(callInterfaceId==null||callInterfaceId==0){
            header = interfaceParamDaoImpl.findByInterfaceIdAndLocation(interfaceId, InterfaceParamLocation.HEADER.getCode());
            path = interfaceParamDaoImpl.findByInterfaceIdAndLocation(interfaceId,InterfaceParamLocation.PATH.getCode());
            body = interfaceParamDaoImpl.findByInterfaceIdAndLocation(interfaceId,InterfaceParamLocation.BODY.getCode());
        }else if(callInterfaceId!=null&&callInterfaceId!=0){
            header = interfaceParamDaoImpl.findByCallInterfaceIdAndInterfaceIdAndLocation(callInterfaceId,interfaceId, InterfaceParamLocation.HEADER.getCode());
            path = interfaceParamDaoImpl.findByCallInterfaceIdAndInterfaceIdAndLocation(callInterfaceId,interfaceId,InterfaceParamLocation.PATH.getCode());
            body = interfaceParamDaoImpl.findByCallInterfaceIdAndInterfaceIdAndLocation(callInterfaceId,interfaceId,InterfaceParamLocation.BODY.getCode());
        }

        dataFrontEnd.setReqMethod(reqMethod);
        dataFrontEnd.setInterfaceName(interfaceName);
        dataFrontEnd.setAccessAddress(accessAddress);
        dataFrontEnd.setHeader(header);
        dataFrontEnd.setPath(path);
        dataFrontEnd.setBody(body);

        return dataFrontEnd;
    }




    public String getUrl(InterfaceDef interfaceDef){

        int reqProtocol = interfaceDef.getReqProtocol();
        String domain = domainDao.findOne(interfaceDef.getDomainId()).getName();
        /** 环境切换转换+++ **/

        StringBuilder sb = new StringBuilder();

        if(reqProtocol==1){
            sb.append("http://");
        }else if(reqProtocol==2){
            sb.append("https://");
        }

        sb.append(domain);
        sb.append("/");
        sb.append(interfaceDef.getUrl());

        return sb.toString();

    }
}
