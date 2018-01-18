package com.workec.ectp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by user on 2017/11/27.
 */
@Entity
@Data
//@NoArgsConstructor    //构造函数
public class InterfaceDef{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int moduleId;

    @NotBlank(message = "接口名不能为空")
    @Size(max = 255, message = "接口名长度不能超过255")
    private String label;

    private int reqMethod;//1 get,2 post,3 put,4 delete,5 dispatch
    private int reqProtocol;//1 http,2 https
    private int domainId;
    private String url;
    private String description;

}
