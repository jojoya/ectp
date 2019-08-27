package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by user on 2018/3/22.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AssertResult {

    private Integer id;
    private String expression;
}
