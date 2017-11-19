package com.workec.ectp.handle;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.exception.ModuleException;
import com.workec.ectp.exception.ModuleFreedomException;
import com.workec.ectp.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Created by user on 2017/11/17.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){

        //模块异常
        if(e instanceof ModuleFreedomException){
            ModuleFreedomException moduleFreedomException = (ModuleFreedomException) e;
            return ResultUtil.error(
                    moduleFreedomException.getCode(),
                    moduleFreedomException.getMessage());
        }else if (e instanceof MethodArgumentTypeMismatchException){
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_TYPE_MISMACH.getCode(),
                    BaseResultEnum.PARAMETER_TYPE_MISMACH.getMessage());
        }else {
            logger.error("【系统异常】{}",e);
            return ResultUtil.error(
                    BaseResultEnum.UNKNOW_ERROR.getCode(),
                    BaseResultEnum.UNKNOW_ERROR.getMessage());
        }
    }
}
