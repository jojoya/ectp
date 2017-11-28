package com.workec.ectp.entity;

import lombok.Data;
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
@Data public class ProjectModuleRelation {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    private int projectId;

    private int moduleId;

    public ProjectModuleRelation(){

    }

}
