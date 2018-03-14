package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
import com.workec.ectp.enums.InterfaceParamLocation;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/3/13.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CallInterfaceInfo {

    private Integer callInterfaceId;
    private InterfaceDef def;
    private List<InterfaceParam> header;
    private List<InterfaceParam> path;
    private List<InterfaceParam> body;

}
