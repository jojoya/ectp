package testDemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/3/23.
 */
public class ListDemo {

    @Test
    public void testSet(){
        List list = new ArrayList<>();
        list.add(0,"00");
        list.add(1,"11");
        list.add(2,"22");
        System.out.println(list);
        list.set(2,"2222");
        System.out.println(list);

        }


    @Test
    public void testAddAll(){
        List list = new ArrayList<>();
        list.add(0,"00");
        list.add(1,"11");
        list.add(2,"22");
        System.out.println(list);

        List list2 = new ArrayList<>();
        list2.add(0,"0000");
        list2.add(1,"1111");
        list2.add(2,"2222");

        list.addAll(0,list2);
        list.addAll(list2);
        System.out.println(list);

    }
}
