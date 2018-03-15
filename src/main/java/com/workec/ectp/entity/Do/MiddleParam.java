package com.workec.ectp.entity.Do;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "middle_param")
@Data
public class MiddleParam implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "expression")
    private String expression;

    @Column(name = "case_id")
    private int caseId;

    @Column(name = "call_interface_id")
    private int callInterfaceId;



}

