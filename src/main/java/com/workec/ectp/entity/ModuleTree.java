package com.workec.ectp.entity;

import java.util.List;

public class ModuleTree {
    private Integer id;
    private String name;
    private List<ModuleTree> childs;

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

    public List<ModuleTree> getChilds() {
        return childs;
    }

    public void setChilds(List<ModuleTree> childs) {
        this.childs = childs;
    }
}
