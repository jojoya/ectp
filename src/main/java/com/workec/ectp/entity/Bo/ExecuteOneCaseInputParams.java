package com.workec.ectp.entity.Bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by user on 2018/3/16.
 */
@Data
public class ExecuteOneCaseInputParams {

    @NotNull(message = "caseId不能为空")
    private Integer caseId;

    @NotNull(message = "applicationEnvironmentId不能为空")
    private Integer applicationEnvironmentId;

    @NotNull(message = "executeUserId不能为空")
    private Integer executeUserId;
}
