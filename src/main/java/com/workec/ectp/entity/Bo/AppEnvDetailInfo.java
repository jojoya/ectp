package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AppEnvDetailInfo {

    private Integer envId;
    private Integer domainId;
    private String domainName;
    private String ip;

}
