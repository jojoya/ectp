package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by user on 2018/3/13.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CallInterfaceResult {
    private Integer callInterfaceId;
    private InterfaceInitData reqInfo;
    private HttpResult result;



    @Override
    public String toString() {
        return "CallInterfaceResult{" +
                "callInterfaceId=" + callInterfaceId +
                ", reqInfo=" + reqInfo +
                ", result=" + result +
                '}';
    }
}
