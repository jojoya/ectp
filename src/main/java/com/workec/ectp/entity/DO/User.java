package com.workec.ectp.entity.DO;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(length=20,nullable = false)
    @NotBlank(message = "名称不能为空")
    @Size(max = 20, message = "名称长度不能超过20")
    private String name;

    @Column(length=20,nullable = false)
    @NotBlank(message = "帐号不能为空")
    @Size(max = 20, message = "帐号长度不能超过20")
    private String account;

    @Column(length=8,nullable = false)
    @NotBlank(message = "密码不能为空")
    @Size(max = 8, message = "密码长度不能超过8")
    private String password;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"name\":\"" + name + '\"' +
                ",\"account\":\"" + account + '\"'+
                ",\"password\":\"" + password + '\"'+
                '}';
    }

}
