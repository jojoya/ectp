package com.workec.ectp.service.Components;

import com.workec.ectp.dao.InterfaceDefDao;
import com.workec.ectp.dao.InterfaceParamDao;
import com.workec.ectp.entity.InterfaceDef;
import com.workec.ectp.entity.InterfaceParam;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.InterfaceDefService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;


@Component
public class InterfaceComponent {

    @Autowired
    private InterfaceDefDao interfaceDefDao;
    @Autowired
    private InterfaceParamDao interfaceParamDao;

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
}
