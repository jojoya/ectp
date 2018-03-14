package com.workec.ectp.entity.DoBak;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "call_interface")
public class CallInterface implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label")
    private String label;

    @Column(name = "case_id")
    private int caseId;

    @Column(name = "interface_id")
    private int interfaceId;

    @Column(name = "location")
    private int location;

    @Column(name = "step")
    private int step;




    @Override
    public String toString() {
        return "CallInterface{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", caseId=" + caseId +
                ", interfaceId=" + interfaceId +
                ", location=" + location +
                ", step=" + step +
                '}';
    }
}
