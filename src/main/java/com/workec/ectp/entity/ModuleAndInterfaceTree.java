package com.workec.ectp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@Data public class ModuleAndInterfaceTree {

    private Integer id;
    private String label;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List/*<ModuleTree>*/ moduleList;
    private List interfaceList;

    @Override
    public String toString() {

        String moduleListInfo = moduleList.size()>0?(", \"moduleList\":" + moduleList.toString()):null;
        String interfaceListInfo = interfaceList.size()>0?(", \"interfaceList\":" + interfaceList.toString()):null;

        return "{" +
                "\"id\":" + id +
                ", \"label\":\"" + label + '\"' +
                moduleListInfo +
                interfaceListInfo +
                '}';

    }
}
