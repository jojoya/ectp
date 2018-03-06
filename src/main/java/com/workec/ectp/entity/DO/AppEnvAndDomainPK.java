package com.workec.ectp.entity.Do;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AppEnvAndDomainPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private int evnId;

    private int domainId;

    public AppEnvAndDomainPK() {
    }

    public AppEnvAndDomainPK(int evnId, int domainId) {
        this.evnId = evnId;
        this.domainId = domainId;
    }

    @Override
    public String toString() {
        return "AppEnvAndDomainPK{" +
                "evnId=" + evnId +
                ", domainId=" + domainId +
                '}';
    }
}
