package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.CaseAssert;
import com.workec.ectp.entity.Do.MiddleParam;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/3/13.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CallInterfaceInfo {

    private Integer callInterfaceId;
    private Integer interfaceId;
    private String interfaceName;
    private String reqMethod;
    private String accessAddress;
    private List<InterfaceParamForCallInfo> header;
    private List<InterfaceParamForCallInfo> path;
    private List<InterfaceParamForCallInfo> body;

    private List<MiddleParam> middleParam;
    private List<CaseAssert> caseAsserts;

}
