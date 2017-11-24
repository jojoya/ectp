package com.workec.ectp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ModuleTree {
    private Integer id;
    private String label;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ModuleTree> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ModuleTree> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleTree> children) {
        this.children = children;
    }
}
