package com.workec.ectp.entity.Bo;

import com.workec.ectp.entity.Bo.KeyValuePair;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

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

    private Integer applicationEnvironmentId;
    private Integer executeUserId;
}
