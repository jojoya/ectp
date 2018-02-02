package com.workec.ectp.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserLoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "帐号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Override
    public String toString() {
        return "{" +
                ",\"account\":\"" + account + '\"'+
                ",\"password\":\"" + password + '\"'+
                '}';
    }

}
