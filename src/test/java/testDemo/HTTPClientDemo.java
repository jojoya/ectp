package testDemo;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

/**
 * Created by caixiaoling on 2018/3/30.
 */
public class HTTPClientDemo {

    @Test
    public void test(){
        String url = "http://www.baidu.com";
        HttpEntityEnclosingRequestBase post =  new HttpPost(url);
        System.out.println("post.getEntity():"+post.getMethod());
        System.out.println("post.getEntity():"+post.getEntity());
    }
}
