package com.workec.ectp.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class TimeEntity implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME",columnDefinition="timestamp default CURRENT_TIMESTAMP")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME",columnDefinition="timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;

    protected TimeEntity() {
    }
}
