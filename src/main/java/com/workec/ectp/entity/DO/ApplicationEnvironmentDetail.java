package com.workec.ectp.entity.Do;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@IdClass(AppEnvAndDomainPK.class)
public class ApplicationEnvironmentDetail extends TimeEntity {

    @Id
    private int evnId;
    @Id
    private int domainId;


    @NotBlank(message = "ip不能为空")
    @Size(min=1, max=16, message = "IP长度1~16")
    @JsonProperty(value = "ip")
    private String ip;



    @Override
    public String toString() {
        return "ApplicationEnvironmentDetail{" +
                "ip='" + ip + '\'' + '}';
    }
}
