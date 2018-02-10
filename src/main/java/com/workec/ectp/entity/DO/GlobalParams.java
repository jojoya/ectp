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
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor //构造函数
public class GlobalParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 64, nullable = false)
    @NotBlank(message = "参数名不能为空")
    @Size(max = 64, message = "参数名称长度不能超过64")
    @JsonProperty(value = "paramName")
    private String paramName;

    @Column(length = 64)
    @Size(max = 64, message = "备注内容长度不能超过64")
    private String remark;

}
