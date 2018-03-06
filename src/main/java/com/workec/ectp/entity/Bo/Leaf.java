package com.workec.ectp.entity.Bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by user on 2018/1/16.
 */
@Data
public class Leaf {
    private int type; //0模块 1接口 2用例
    private int id;
    private int parentId;

    @NotBlank(message = "name不能为空")
    @Size(max = 200, message = "name长度不能超过200")
    private String name;
}
