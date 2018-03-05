package com.workec.ectp.entity.Bo;

import lombok.Data;

/**
 * Created by user on 2018/2/25.
 */
@Data
public class GlobalParamsDataInfo {

    private int id;
    private int userId;
    private int dbEnvId;
    private String dbEnvName;
    private int globalParamId;
    private String paramName;
    private String paramValue;
    private String remark;

}
