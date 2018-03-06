package com.workec.ectp.entity.Bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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
