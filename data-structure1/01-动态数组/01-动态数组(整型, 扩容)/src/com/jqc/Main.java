package com.jqc;

public class Main {

    public static void main(String[] args) {
        ArrayList array = new ArrayList(5);
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }
         System.out.println(array);


    }

    public void test1() {
        ArrayList array = new ArrayList(5);
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
}
