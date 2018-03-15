package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.InterfaceDefDao;
import com.workec.ectp.dao.jpa.InterfaceParamDao;
import com.workec.ectp.entity.Bo.InterfaceDebugData;
import com.workec.ectp.entity.Bo.InterfaceInitDataFrontEnd;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
import com.workec.ectp.entity.Bo.ParamIdList;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.components.InterfaceComponent;
import com.workec.ectp.enums.InterfaceParamLocation;
import com.workec.ectp.service.InterfaceService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceComponent interfaceComponent;
    @Autowired
    private InterfaceDefDao interfaceDefDao;
    @Autowired
    private InterfaceParamDao interfaceParamDao;


    /*修改接口信息*/
    public Result<InterfaceDebugData> updateInterface(@Valid InterfaceDebugData itf, BindingResult bindingResult) {

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
        InterfaceDebugData result = new InterfaceDebugData();
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
            //替换接口id
            param.setInterfaceDefId(defId);
            if(param.getFormat()==1 && param.getLocation()==2 && (param.getParamName()==null||param.getParamName()=="")){
                //校验form参数名为空，不保存或修改
            }else if(param.getFormat()==0 && param.getLocation()==2 && param.getParamName()!=null&& param.getParamName()!=""){
                //校验json参数名不为空，不保存或修改
            }else{
                InterfaceParam resultParam = interfaceComponent.saveParam(param,bindingResult);
                resultParams.add(resultParam);
            }
        }
        return resultParams;
    }

    /* 查询接口信息 */
    public Result<InterfaceDebugData> getInterface(Integer id){

        //获取def
        InterfaceDef resultDef = interfaceDefDao.findOne(id);

        //获取params
        List<InterfaceParam> header = interfaceParamDao.findByInterfaceDefIdAndLocation(id, InterfaceParamLocation.HEADER.getCode());
        List<InterfaceParam> path = interfaceParamDao.findByInterfaceDefIdAndLocation(id,InterfaceParamLocation.PATH.getCode());
        List<InterfaceParam> body = interfaceParamDao.findByInterfaceDefIdAndLocation(id,InterfaceParamLocation.BODY.getCode());

        //组装结果
        InterfaceDebugData result = new InterfaceDebugData();
        result.setDef(resultDef);
        result.setHeader(header);
        result.setPath(path);
        result.setBody(body);

        return ResultUtil.success(result);
    }

    @Override
    public Result deleteParams(ParamIdList paramIdList) {
        for (Integer id:paramIdList.getIds()) {
            interfaceParamDao.delete(id);
        }
        return ResultUtil.success();
    }


    @Override
    public Result<InterfaceInitDataFrontEnd> getInterfaceStructure(Integer interfaceId) {
        if(!interfaceDefDao.exists(interfaceId)){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        return ResultUtil.success(interfaceComponent.getInterfaceStructure(0,interfaceId));
    }
}
