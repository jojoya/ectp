package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by user on 2018/3/13.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CallInterfaceResult {
    private Integer executeStatus;
    private String executeMsg;

    private Integer callInterfaceId;
    private InterfaceInitDataBackEnd reqInfo;
    private HttpResult result;

    private Integer location;
    private Integer step;
    private String label;


    @Override
    public String toString() {
        return "CallInterfaceResult{" +
                "callInterfaceId=" + callInterfaceId +
                ", reqInfo=" + reqInfo +
                ", result=" + result +
                '}';
    }
}
