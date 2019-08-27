package com.workec.ectp.configuration;

/**
 * 系统配置
 */
public class Configuration {
    /**
     *是否启用环境映射
     */
    private static final Boolean envChange = false;

    public static Boolean getEnvChange() {
        return envChange;
    }
}
