package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.CallInterface;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/3/14.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)    //字段为空也进行序列化
public class GroupedCallInterface {

    private List<CallInterface> pres;
    private List<CallInterface> test;
    private List<CallInterface> posts;
}
