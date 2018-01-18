package com.workec.ectp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by user on 2017/11/22.
 */
@Entity
@Data @NoArgsConstructor //构造函数
public class ProjectModuleRelation  implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "project_id",nullable = false)
    private int projectId;

    @Column(name = "module_id",nullable = false)
    private int moduleId;

}
