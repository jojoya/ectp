package com.workec.ectp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@Data public class ModuleTree {

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    private String label;
    private int type;
    private List<ModuleTree> children;

    @Override
    public String toString() {

        String moduleListInfo = children!=null?(", \"children\":" + children.toString()):(", \"children\":" + null);
        return "{" +
                "\"id\":" + id +
                ", \"label\":\"" + label +
                ", \"type\":\"" + type +
                moduleListInfo +
                '}';

    }
}
