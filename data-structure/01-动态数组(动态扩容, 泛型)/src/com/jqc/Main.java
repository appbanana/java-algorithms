package com.jqc;

import com.jqc.list.ArrayList;

public class Main {

    public static void main(String[] args) {
        test1();
        // 测试扩容
        test2();
        // 测试泛型
        test3();
    }

    public static void test1() {
        ArrayList<Integer> array = new ArrayList(5);
        System.out.println(array);

        array.add(5);
        array.add(6);
        array.add(66);
        array.add(166);
        System.out.println(array);

        array.set(0, 88);
        System.out.println(array);

        array.set(array.size() - 1, 100);
        System.out.println(array);

        array.remove(2);
        System.out.println(array);

        System.out.println(array.get(2));

        array.clear();
        System.out.println(array);
    }

    public static void test2() {
        ArrayList<Integer> array = new ArrayList(5);
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }
        System.out.println(array);
    }

    public static void test3() {
        ArrayList<Object> array  = new ArrayList<>();

        array.add("10");
        array.add(15);
        array.add(null);
        System.out.println(array);
        System.out.println(array.contain(null));
    }


}
