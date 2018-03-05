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

@Entity(name = "t_domain")
@Data
@NoArgsConstructor //构造函数
public class Domain extends TimeEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length=50,nullable = false)
    @NotBlank(message = "名称不能为空")
    @Size(max = 50, message = "名称长度不能超过50")
    @JsonProperty(value = "value")
    private String name;

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
