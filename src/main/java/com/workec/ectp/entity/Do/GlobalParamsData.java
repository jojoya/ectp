package com.workec.ectp.entity.Do;

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
public class GlobalParamsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 64)
    private int userId;

    @Column(length = 64)
    private int dbEnvId;    //1测试 2开发 3现网

    @Column(length = 64)
    private int globalParamId;

    @NotBlank(message = "参数值不能为空")
    @Size(max = 64, message = "参数值长度不能超过64")
    private String paramValue;


}
