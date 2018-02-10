package com.workec.ectp.entity.dto;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResult{

    private Integer statusCode;

    private String reasonPhrase;

    private Map headers;

    private Object body;

}