package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.workec.ectp.entity.Do.CaseAssert;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/3/13.
 */

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CaseExecuteResult {

    private AssertResult assertResult;
//    private List<CallInterfaceResult> preResults;
    private List<CallInterfaceResult> testResult;
//    private List<CallInterfaceResult> postResults;


    @Override
    public String toString() {
        return "CaseExecuteResult{" +
                ", testList=" + testResult +
                '}';
    }
}
