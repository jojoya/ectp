package com.workec.ectp.entity.Do;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "case_assert")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CaseAssert implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="type")
    private int type;       //1响应断言

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "case_id")
    private int caseId;

    @Column(name = "call_interface_id")
    private int callInterfaceId;

    @Column(name="rsp_resource")
    private int rspResource;        //断言数据来源：1主请求

    @Column(name="rsp_location")
    private int rspLocation;        //断言数据位置：1header 2body

    @Column(name="rule")
    private int rule;               //断言规则：1包含

    @Column(name = "expression")
    private String expression;      //断言表述（内容、表达式）


}

