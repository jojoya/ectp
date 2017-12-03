package com.workec.ectp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@Data public class ModuleTree {

    private Integer id;
    private String label;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ModuleTree> moduleList;

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
