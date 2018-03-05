package com.workec.ectp.entity.dto;

import com.workec.ectp.entity.Bo.KeyValuePair;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/2/10.
 */
@Data
public class HttpDebugInformation {

    private Integer method;

    @NotBlank(message = "url不能为空")
    private String url;
    private List<KeyValuePair> paths;
    private List<KeyValuePair> headers;
    private Object bodys;
}
