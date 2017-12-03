package com.workec.ectp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by user on 2017/11/22.
 */
@Entity
@Data @NoArgsConstructor //构造函数
public class Project {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length=20,nullable = false)
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 20, message = "项目名称长度不能超过20")
    private String value;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"value\":\"" + value + '\"' +
                '}';
    }

}
