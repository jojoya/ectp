package testDemo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2018/1/19.
 */
public class MapDemo {

    public static void main(String [] args){

        Map map = new HashMap();
        map.put("id",1);
        map.put("name","jojoya");
        map.put("name","jojoya");

        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();

            String key = String.valueOf(entry.getKey());

            String value = String.valueOf(entry.getValue());

            System.out.println("Key = " + key + ", Value = " + value);

        }
    }
}
