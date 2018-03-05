package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by user on 2018/2/25.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class GlobalParamsDataInfo {

    private Integer paramId;
    private String paramName;
    private String remark;

    private Integer paramDataId;
    private Integer dbEnvId;
    private String dbEnvName;
    private String paramValue;
    private Integer userId;

}
