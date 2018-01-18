package com.workec.ectp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by user on 2018/1/16.
 */
@Entity
@Data
//@NoArgsConstructor    //构造函数
public class InterfaceParam  implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String paramName;

    @JsonIgnore
    private int location;   //0Header 1Path 2Body
    private int format;     //0Json 1Form
    private String remark;

    @JsonIgnore
    private int interfaceDefId;
}
