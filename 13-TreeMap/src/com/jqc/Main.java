package com.jqc;
import com.jqc.file.FileInfo;
import com.jqc.file.Files;
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
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);
        map.print();
        map.traversal(new Visitor<String, Integer>() {
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
        System.out.println("----------remove------");
        map.remove("a");
        map.remove("c");

        map.traversal(new Visitor<String, Integer>() {
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }


    static void test2() {

        FileInfo fileInfo = Files.read("/Users/JQC/Desktop/src/java/util",
                new String[]{"java"});

        System.out.println("文件数量：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词数量：" + words.length);

        Map<String, Integer> map = new TreeMap<>();
        for (int i = 0; i < words.length; i++) {
            Integer count = map.get(words[i]);
            count = (count == null) ? 1 : (count + 1);
            map.put(words[i], count);
        }


        int count = 0;
        for (String word : words) {
            Integer i = map.get(word);
            count += i == null ? 0 : i;
            map.remove(word);
        }
        System.out.println(count + ", " + words.length);
        System.out.println(map.size());

        Asserts.test(count == words.length);
        Asserts.test(map.size() == 0);

    }

}
