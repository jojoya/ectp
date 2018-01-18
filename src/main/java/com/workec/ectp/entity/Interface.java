package com.workec.ectp.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by user on 2018/1/17.
 */
@Data
public class Interface {

    private InterfaceDef def;
    private List<InterfaceParam> header;
    private List<InterfaceParam> path;
    private List<InterfaceParam> body;}
