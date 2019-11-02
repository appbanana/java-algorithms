package com.jqc;

import com.jqc.file.FileInfo;
import com.jqc.file.Files;
import com.jqc.set.LinkSet;
import com.jqc.set.Set;
import com.jqc.set.Set.Visitor;
import com.jqc.set.TreeSet;
import com.jqc.tools.Times;

public class Main {

    public static void main(String[] args) {
//        test1(new LinkSet<Integer>());
//        test1(new TreeSet<Integer>());

        test2();
    }

    static void test1(Set<Integer> set) {
        set.add(10);
        set.add(11);
        set.add(11);
        set.add(12);
        set.add(10);

        set.traversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
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

        // 耗时：69.174秒 有点有点久 请耐心等待
        Times.test("ListSet", new Times.Task() {
            public void execute() {
                testSet(new LinkSet<>(), words);
            }
        });

        Times.test("TreeSet", new Times.Task() {
            public void execute() {
                testSet(new TreeSet<>(), words);
            }
        });

    }
    static void testSet(Set<String> set, String[] words) {

        for (int i = 0; i < words.length; i++) {
            set.add(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.contains(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.remove(words[i]);
        }
    }
}
