package testDemo;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2018/1/19.
 */

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes=Deserializers.Base.class)
public class MapDemo {

    public static void main(String [] args){

        Map map = new HashMap();
        map.put("id",1);
        map.put(2,2);
        map.put("name","jojoya");
        map.put("name","jojoya");

        System.out.println("id:"+map.get(3));

        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();

            String key = String.valueOf(entry.getKey());

            String value = String.valueOf(entry.getValue());

            System.out.println("Key = " + key + ", Value = " + value);

        }
    }
}
