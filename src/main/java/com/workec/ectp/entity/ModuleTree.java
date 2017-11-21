package com.workec.ectp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ModuleTree {
    private Integer id;
    private String name;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ModuleTree> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModuleTree> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleTree> children) {
        this.children = children;
    }
}
