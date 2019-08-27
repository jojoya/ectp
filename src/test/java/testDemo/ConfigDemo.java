package testDemo;

import com.workec.ectp.configuration.Configuration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by user on 2018/3/29.
 */
public class ConfigDemo {

    @Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${ecConfig.appEnvChange}")
    private Integer appEnvChange;


    @Test
    public void test(){

        System.out.println(Configuration.getEnvChange());
    }

    public Integer getAppEnvChange() {
        return appEnvChange;
    }
}
