package com.workec.ectp.handle;


import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.exception.ModuleFreedomException;
import com.workec.ectp.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
        }else if (e instanceof HttpMessageNotReadableException){
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }else if(e instanceof HttpRequestMethodNotSupportedException){
            return ResultUtil.error(
                    BaseResultEnum.REQUEST_METHOD_NOT_SUPPORT.getCode(),
                    BaseResultEnum.REQUEST_METHOD_NOT_SUPPORT.getMessage());
        }else if(e instanceof HttpMediaTypeNotSupportedException){
            return ResultUtil.error(
                    BaseResultEnum.REQUEST_CONTENT_TYPE_NOT_SUPPORT.getCode(),
                    BaseResultEnum.REQUEST_CONTENT_TYPE_NOT_SUPPORT.getMessage());
        }else if(e instanceof EmptyResultDataAccessException){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }else if(e instanceof DataIntegrityViolationException){
            return ResultUtil.error(
                    BaseResultEnum.DATA_MISSING.getCode(),
                    BaseResultEnum.DATA_MISSING.getMessage());
        }else if(e instanceof InvalidDataAccessApiUsageException){
            return ResultUtil.error(
                    BaseResultEnum.INVALID_DATA_ACCESS.getCode(),
                    BaseResultEnum.INVALID_DATA_ACCESS.getMessage());
        }else{
            logger.error("【系统异常】{}",e);
            return ResultUtil.error(
                    BaseResultEnum.UNKNOW_ERROR.getCode(),
                    BaseResultEnum.UNKNOW_ERROR.getMessage());
        }
    }
}
