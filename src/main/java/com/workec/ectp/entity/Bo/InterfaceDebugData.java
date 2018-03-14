package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.InterfaceParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2018/1/17.
 */
@Data
public class InterfaceDebugData implements Serializable{

    @JsonInclude(Include.ALWAYS)
    private InterfaceDef def;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> header;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> path;

    @JsonInclude(Include.ALWAYS)
    private List<InterfaceParam> body;

    @Override
    public String toString() {
        return "InterfaceDebugData{" +
                "def=" + def +
                ", header=" + header +
                ", path=" + path +
                ", body=" + body +
                '}';
    }
}
