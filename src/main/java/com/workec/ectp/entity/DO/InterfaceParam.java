package com.workec.ectp.entity.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by user on 2018/1/16.
 */
@Entity
@Data
@NoArgsConstructor    //构造函数
public class InterfaceParam  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String paramName;

    @Size(max = 2000, message = "maxSize(value)>2000")
    private String value;

    @JsonIgnore
    private int interfaceDefId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int location;   //0Header 1Path 2Body

    private int format;     //0Json 1Form
    private String remark;



}
