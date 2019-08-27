package com.workec.ectp.entity.Do;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "middle_param")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class MiddleParam implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer type;   //1正则提取 2Json提取
    private String  name;
    private Integer caseId;
    private Integer callInterfaceId;
    private Integer rspResource;    //数据来源：1主请求
    private Integer rspLocation;    //数据位置：1header 2body
    @NotBlank
    private String  expression;  //表述（内容、表达式）
//    @NotBlank
    private Integer templateNum; //正则提取的模板
//    @NotBlank
    private Integer valueNum;    //正则模板匹配结果中的哪一个值

}

