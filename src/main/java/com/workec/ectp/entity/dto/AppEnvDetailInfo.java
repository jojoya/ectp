package com.workec.ectp.entity.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AppEnvDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer envId;

    private Integer domainId;

    private String ip;


}
