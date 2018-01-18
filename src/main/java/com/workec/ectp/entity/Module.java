package com.workec.ectp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity @NoArgsConstructor //实例不设置构造函数
@Data
public class Module extends TimeEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "模块名称不能为空")
    @Size(max = 50, message = "模块名称长度不能超过50")
    private String label;

    private int parentId;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                "\"name\":\"" + label + '\"' +
                ", \"parentId\":" + parentId +
                '}';
    }

}
