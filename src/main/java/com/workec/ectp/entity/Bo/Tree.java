package com.workec.ectp.entity.Bo;

import lombok.Data;
import java.util.List;

@Data
public class Tree {

    private int id;
    private String label;
    private int type;
    private List<Tree> children;

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
