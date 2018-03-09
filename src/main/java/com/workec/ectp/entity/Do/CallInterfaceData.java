package com.workec.ectp.entity.Do;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "call_interface_data")
public class CallInterfaceData implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "call_interface_id")
    private int callInterfaceId;

    @Column(name = "params_key_id")
    private int paramsKeyId;

    @Column(name = "params_value")
    private String paramsValue;



}
