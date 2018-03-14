package com.workec.ectp.entity.Do;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by user on 2017/11/27.
 */
@Entity
@Data
@NoArgsConstructor    //构造函数
@JsonInclude(JsonInclude.Include.ALWAYS)    //字段为空也进行序列化
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })     //反序列化指定序列化器
public class InterfaceDef implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int moduleId;

    @NotBlank(message = "接口名不能为空")
    @Size(max = 255, message = "接口名长度不能超过255")
    private String label;

    private int reqMethod;//1 get,2 post
    private int reqProtocol;//1 http,2 https
    private int domainId;
    private String url;
    private String description;

}
