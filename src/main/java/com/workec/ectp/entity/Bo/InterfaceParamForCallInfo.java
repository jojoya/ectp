package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by user on 2018/3/14.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)    //字段为空也进行序列化
public class InterfaceParamForCallInfo {

    private Integer paramId;
    private String paramName;

    private Integer valueId;
    private String value;

    private Integer format;     //0Json 1Form
    private String remark;








    @Override
    public String toString() {
        return "InterfaceParamForCallInfo{" +
                "paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                ", valueId=" + valueId +
                ", value='" + value + '\'' +
                ", format=" + format +
                ", remark='" + remark + '\'' +
                '}';
    }
}
