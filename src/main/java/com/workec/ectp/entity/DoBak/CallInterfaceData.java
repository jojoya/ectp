package com.workec.ectp.entity.DoBak;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "call_interface_data")
public class CallInterfaceData implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "call_interface_id")
    private int callInterfaceId;

    @Column(name = "param_key_id")
    private int paramKeyId;

    @Column(name = "params_value")
    private String paramsValue;



}
