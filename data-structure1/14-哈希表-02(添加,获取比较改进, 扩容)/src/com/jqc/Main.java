package com.jqc;
import com.jqc.file.FileInfo;
import com.jqc.file.Files;
import com.jqc.map.HashMap;
import com.jqc.map.Map;
import com.jqc.map.TreeMap;

import com.jqc.map.Map.Visitor;
import com.jqc.tools.Asserts;

public class Main {

    public static void main(String[] args) {
//        test1();

        test2();

    }

    static void test1() {
        // 如果想要这两个person一样 那你必须要重写hashCode和equals方法 这个还是主要看业务需求，看你想怎么来规定
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");

        Map<Object, Object> map = new HashMap<>();
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jack", 3);
        map.put(6, 4);
        map.put(p1, 5);
        map.put(p2, 7);
        map.put(null, 6);

        System.out.println(map.size());
        System.out.println(map.remove("jack"));
        System.out.println(map.get("jack"));
        System.out.println(map.size());
        System.out.println("===============");

        System.out.println(map.containsKey(p1));
        System.out.println(map.containsKey(null));
        System.out.println(map.containsValue(6));
        System.out.println(map.containsValue(1));

        System.out.println(map.get(p1));

        System.out.println("===============");

        map.traversal(new Map.Visitor<Object, Object>() {
            @Override
            public boolean visit(Object key, Object value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test2() {
        HashMap<Object, Integer> map = new HashMap<>();
        for (int i = 1; i <= 19; i++) {
            map.put(new Key(i), i);
        }

        map.print();
        // 测试发现 这样写hash还是有问题的 还是需要改进
        for (int i = 1; i <= 19; i++) {
            Asserts.test(map.get(new Key(i)) == i);
        }


    }

}
