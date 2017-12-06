package com.workec.ectp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@Data public class ModuleTree {

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String label;
    private List<ModuleTree> moduleList;
    private List interfaceList;

    @Override
    public String toString() {

        String moduleListInfo = moduleList!=null?(", \"moduleList\":" + moduleList.toString()):(", \"moduleList\":" + null);
        return "{" +
                "\"id\":" + id +
                ", \"label\":\"" + label + '\"' +
                moduleListInfo +
                '}';

    }
}
