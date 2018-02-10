package testDemo;

import org.hibernate.annotations.SQLUpdate;
import org.junit.Test;

import java.util.*;

/**
 * Created by user on 2018/2/10.
 */
public class SetDemo {

    @Test
    public void testSet(){
        Set<String> set = new HashSet<>();//TreeSet方法相同
        set.add( "C");
        set.add( "A");
        set.add( "A");
        set.add( "A");
        set.add( "B");
        set.add( "D");
        set.add( "E");
        set.add( "F");
        System. out.println( set);
    }

    @Test
    public void testMap(){
        Map<String,Integer> map = new HashMap<>();//TreeSet方法相同
        map.put( "C",1);
        map.put( "A",2);
        map.put( "A",3);
        map.put( "A",4);
        map.put( "B",5);
        map.put( "D",6);
        map.put( "E",7);
        map.put( "F",8);
        System. out.println( map);
    }

    @Test
    public void testList(){
        List<String> list = new ArrayList<>();//TreeSet方法相同
        list.add( "C");
        list.add( "A");
        list.add( "A");
        list.add( "A");
        list.add( "B");
        list.add( "D");
        list.add( "E");
        list.add( "F");
        System. out.println( list);
    }

}
