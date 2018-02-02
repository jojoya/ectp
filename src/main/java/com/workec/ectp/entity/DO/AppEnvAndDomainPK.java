package com.workec.ectp.entity.DO;

import javax.persistence.Embeddable;
import java.io.Serializable;


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

    public int getEvnId() {
        return evnId;
    }

    public void setEvnId(int evnId) {
        this.evnId = evnId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
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
