package com.workec.ectp.entity.Bo;


import lombok.Data;

@Data
public class CallInterfaceAndMiddleValues {

    private int caseId;
    private int callInterfaceId;
    private String label;
    private int location;
    private int step;
    private int middleValueId;
    private String name;
    private String expression;

}


