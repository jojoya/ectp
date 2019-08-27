package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/3/15.
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class InterfaceInitDataFrontEnd {

    //请求方法
    String reqMethod;
    String interfaceName;
    //访问地址
    String accessAddress;

    //获取params
    List<InterfaceParamForCallInfo> header;
    List<InterfaceParamForCallInfo> path;
    List<InterfaceParamForCallInfo> body;
}
