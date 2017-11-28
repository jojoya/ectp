package com.workec.ectp.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "module")
@Data public class Module extends TimeEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "label",length=50,nullable = false)
    @NotBlank(message = "模块名称不能为空")
    @Size(max = 50, message = "模块名称长度不能超过50")
    private String label;

    @Column(name = "parent_id",length=50,nullable = false)
    private int parentId;


     //必须要有构造函数
    public Module(){

    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                "\"name\":\"" + label + '\"' +
                ", \"parentId\":" + parentId +
                '}';
    }

}
