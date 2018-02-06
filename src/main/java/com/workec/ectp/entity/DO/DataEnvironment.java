package com.workec.ectp.entity.DO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by user on 2018/2/1.
 */

@Entity
@Data
@NoArgsConstructor //构造函数
public class DataEnvironment extends TimeEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length=32,nullable = false)
    @NotBlank(message = "名称不能为空")
    @Size(max = 32, message = "名称长度不能超过32")
    @JsonProperty(value = "value")
    private String name;

    @Override
    public String toString() {
        return "DataEnvironment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
