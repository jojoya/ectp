package com.workec.ectp.entity.Do;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppEnvAndDomainPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private int evnId;

    private int domainId;

    @Override
    public String toString() {
        return "AppEnvAndDomainPK{" +
                "evnId=" + evnId +
                ", domainId=" + domainId +
                '}';
    }
}
