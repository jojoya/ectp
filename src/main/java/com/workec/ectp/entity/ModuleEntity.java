package com.workec.ectp.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class ModuleEntity extends TimeEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "模块名称不能为空")
    @Size(min=1,max=50,message = "模块名称长度必须在1-50之间")
    private String name;

    private int parantId;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParantId() {
        return parantId;
    }

    public void setParantId(int parantId) {
        this.parantId = parantId;
    }


}
