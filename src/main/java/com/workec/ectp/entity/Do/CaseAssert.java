package com.workec.ectp.entity.Do;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "case_assert")
@Data
public class CaseAssert implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="type")
    private int type;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "case_id")
    private int caseId;

    @Column(name = "call_interface_id")
    private int callInterfaceId;

    @Column(name="rsp_resource")
    private int rspResource;

    @Column(name="rsp_location")
    private int rspLocation;

    @Column(name="rule")
    private int rule;

    @Column(name = "expression")
    private String expression;


}

