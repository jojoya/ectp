package com.workec.ectp.entity.Do;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
//@Data
@NoArgsConstructor //构造函数
public class ApplicationEnvironmentDetail extends TimeEntity {

    @NotBlank(message = "ip不能为空")
    @Size(min=1, max=16, message = "IP长度1~16")
    @JsonProperty(value = "ip")
    private String ip;

    @EmbeddedId
    private AppEnvAndDomainPK pk;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @EmbeddedId
    public AppEnvAndDomainPK getPk() {
        return pk;
    }

    @EmbeddedId
    public void setPk(AppEnvAndDomainPK pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "ApplicationEnvironmentDetail{" +
                "ip='" + ip + '\'' +
                ", pk=" + pk +
                '}';
    }
}
