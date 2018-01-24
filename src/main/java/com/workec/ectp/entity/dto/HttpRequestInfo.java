package com.workec.ectp.entity.dto;

import lombok.Data;
import org.json.JSONObject;

/**
 * Created by user on 2017/12/22.
 */
@Data
public class HttpRequestInfo {

    private String url;
    private JSONObject urlParam;
    private JSONObject body;
    private JSONObject header;
    private String method;
}
