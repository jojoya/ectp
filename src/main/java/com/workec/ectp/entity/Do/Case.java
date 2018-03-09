package com.workec.ectp.entity.Do;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "t_case")
@Data
public class Case implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private int type;

    @Column(name = "interface_id")
    private String interfaceId;

    @Column(name = "description")
    private String description;



}

