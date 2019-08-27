package testDemo;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.workec.ectp.utils.ToolsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by user on 2018/1/19.
 */

//@RunWith(SpringRunner.class)
//////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(classes=Deserializers.Base.class)
public class MapDemo {

    @Test
    public  void testMap(){

        Map map = new HashMap();
        map.put("id",1);
        map.put(2,2);
        map.put("name","jojoya");
        map.put("name","jojoya");

        System.out.println(map.keySet());
/*        System.out.println("id:"+map.get(3));

        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();

            String key = String.valueOf(entry.getKey());

            String value = String.valueOf(entry.getValue());

            System.out.println("Key = " + key + ", Value = " + value);

        }*/
    }

    @Test
    public  void testList() {

        ToolsUtil toolsUtil = new ToolsUtil();
        Map map = new HashMap();
        map.put("id",1);
        map.put(2,2);
        map.put("name","jojoya");
        map.put("name","jojoya");
        toolsUtil.mapToHeader(map);




        List list = new ArrayList();
        list.add("1:111");
        list.add("2:222");

        System.out.println(list);
    }
}
