package com.workec.ectp.service.impl;

import com.workec.ectp.dao.InterfaceDefDao;
import com.workec.ectp.dao.InterfaceParamDao;
import com.workec.ectp.entity.*;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.Components.InterfaceComponent;
import com.workec.ectp.service.InterfaceService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceComponent interfaceComponent;
    @Autowired
    private InterfaceDefDao interfaceDefDao;
    @Autowired
    private InterfaceParamDao interfaceParamDao;


    /*修改接口信息*/
    public Result<Interface> updateInterface(@Valid Interface itf, BindingResult bindingResult) {

        System.out.println(itf);
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //保存接口信息
        InterfaceDef def = itf.getDef();  //从请求中获取def信息
        InterfaceDef resultDef = interfaceComponent.saveDef(def,bindingResult);

        int defId = resultDef.getId();  //从返回结果中获取接口id

        //从请求中获取params信息
        List<InterfaceParam> header_param = itf.getHeader();
        List<InterfaceParam> path_param = itf.getPath();
        List<InterfaceParam> body_param = itf.getBody();
        //获取结果params
        List<InterfaceParam> result_header = header_param==null?null:saveParams(defId, header_param, bindingResult);
        List<InterfaceParam> result_path = path_param==null?null:saveParams(defId, path_param, bindingResult);
        List<InterfaceParam> result_body = body_param==null?null:saveParams(defId, body_param, bindingResult);

        //组装结果interface
        Interface result = new Interface();
        result.setDef(resultDef);
        result.setHeader(result_header);
        result.setPath(result_path);
        result.setBody(result_body);

        return ResultUtil.success(result);
    }

    /*批量保存字段信息*/
    private List<InterfaceParam> saveParams(Integer defId,List<InterfaceParam> params,BindingResult bindingResult){
        List<InterfaceParam> resultParams = new ArrayList();
        for (InterfaceParam param:params) {
            param.setInterfaceDefId(defId);  //替换接口id
            InterfaceParam resultParam = interfaceComponent.saveParam(param,bindingResult);
            resultParams.add(resultParam);
        }
        return resultParams;
    }

    /* 查询接口信息 */
    public Result<Interface> getInterface(Integer id){

        //获取def
        InterfaceDef resultDef = interfaceDefDao.findOne(id);

        //获取params
        List<InterfaceParam> header = interfaceParamDao.findByInterfaceDefIdAndLocation(id,0);
        List<InterfaceParam> path = interfaceParamDao.findByInterfaceDefIdAndLocation(id,1);
        List<InterfaceParam> body = interfaceParamDao.findByInterfaceDefIdAndLocation(id,2);

        //组装结果
        Interface result = new Interface();
        result.setDef(resultDef);
        result.setHeader(header);
        result.setPath(path);
        result.setBody(body);

        return ResultUtil.success(result);
    }

    @Override
    public Result deleteParams(String ids) {
        System.out.println("ids="+ids);
        String [] idStr = ids.split(",");
        for (String id:idStr) {
            System.out.println("id="+id);
            interfaceParamDao.delete(Integer.parseInt(id));
        }
        return ResultUtil.success();
    }


//    public Result<InterfaceParam> updateInterfaceParam(@Valid InterfaceParam param, BindingResult bindingResult){
//
//        InterfaceParam resultParam = interfaceComponent.saveParam(param,bindingResult);
//        return ResultUtil.success(resultParam);
//
//    }



}
