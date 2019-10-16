package com.jqc;

public class Main {

    public static void main(String[] args) {
        ArrayList<Object> array  = new ArrayList<>();

        array.add("10");
        array.add(15);
        array.add(null);
        System.out.println(array);
        System.out.println(array.contain(null));


    }

}
