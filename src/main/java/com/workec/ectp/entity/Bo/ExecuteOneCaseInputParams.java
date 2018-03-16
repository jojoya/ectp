package com.workec.ectp.entity.Bo;

import lombok.Data;

/**
 * Created by user on 2018/3/16.
 */
@Data
public class ExecuteOneCaseInputParams {

    private Integer caseId;
    private Integer applicationEnvironmentId;
    private Integer executeUserId;
}
