package com.workec.ectp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by user on 2017/11/27.
 */
@Entity(name = "interface_main")
@Data @NoArgsConstructor    //构造函数
public class InterfaceMain extends TimeEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Integer id;

    @NotBlank(message = "方法名不能为空")
    @Size(max = 200, message = "方法名长度不能超过200")
    @Column(name = "value",nullable = false,length=200)
    private String value;

    @Column(name = "request_method",nullable = false)
    private Integer requestMethod;

    @Column(name = "description")
    private String description;

    @Column(name = "domain_id",nullable = false)
    private Integer domainId;

    @Column(name = "module_id",nullable = false)
    private Integer moduleId;

}
