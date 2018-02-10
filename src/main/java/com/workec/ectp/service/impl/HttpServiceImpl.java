package com.workec.ectp.service.impl;

import com.workec.ectp.entity.dto.HttpDebugInformation;
import com.workec.ectp.entity.dto.HttpResult;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.components.HttpAPIComponent;
import com.workec.ectp.service.HttpService;
import com.workec.ectp.utils.ResultUtil;
import com.workec.ectp.utils.ToolsUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;


@Service
public class HttpServiceImpl implements HttpService {

    @Autowired
    HttpAPIComponent httpAPIComponent;

    @Override
    public Result<HttpResult> doDebug(HttpDebugInformation httpDebugInformation, BindingResult bindingResult){

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Integer method = httpDebugInformation.getMethod();
        String url = httpDebugInformation.getUrl();
        Map<String, Object> paths = httpDebugInformation.getPaths();
        Map<String, Object> headers = httpDebugInformation.getHeaders();
        JSONObject bodys = httpDebugInformation.getBodys();

        HttpResult httpResult = null;

        if(method==1) {
            //get请求
            httpResult = httpAPIComponent.doGet(url, paths, headers);
            return ResultUtil.success(httpResult);
        }else if(method==2){
            //postForm请求
            try {
                httpResult = httpAPIComponent.doPostForm(url, paths, headers, ToolsUtil.transferMap(bodys));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultUtil.success(httpResult);
        }else if(method==3){
            //postJson请求
            httpResult = httpAPIComponent.doPostJson(url, paths, headers, bodys.toString());
            return ResultUtil.success(httpResult);
        }else{
            return null;
        }
    }
}
