package com.workec.ectp.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workec.ectp.entity.DO.InterfaceDef;
import com.workec.ectp.entity.DO.InterfaceParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2018/1/17.
 */
@Data
public class Interface implements Serializable{

    @JsonInclude(Include.ALWAYS)
    private InterfaceDef def;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> header;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> path;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> body;}
