package com.jqc;

import com.jqc.list.SingleList;

public class Main {

    public static void main(String[] args) {
        SingleList<Integer> list = new SingleList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);

        list.add(0, 100);
        list.add(101);
        list.remove(0);
        list.remove(list.size() - 1);
        list.remove(2);
        System.out.println(list);

    }

}
