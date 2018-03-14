package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
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
    private List<InterfaceParam> header;
    private List<InterfaceParam> path;
    private List<InterfaceParam> body;

}
