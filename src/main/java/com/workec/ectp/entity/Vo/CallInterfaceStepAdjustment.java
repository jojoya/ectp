package com.workec.ectp.entity.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.CallInterface;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CallInterfaceStepAdjustment {

    @NotNull
    private int activeCallInterfaceId;    //被操作的步骤id

    @NotNull
    private int passiveCallInterfaceId;    //被交换位置的步骤id

    @NotNull
    private int activeStepNo;               //被操作的步骤编号

    @NotNull
    private int passiveStepNo;               //被交换位置的步骤编号
}

