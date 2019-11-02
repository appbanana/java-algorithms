package com.jqc;

import com.jqc.queue.PriorityQueue;

import java.util.Comparator;


public class Main {

    public static void main(String[] args) {

        test1();
    }

    static void test1() {

        PriorityQueue<Person1> queue = new PriorityQueue<>(new Comparator<Person1>() {
            @Override
            public int compare(Person1 o1, Person1 o2) {
                return o1.getBoneBreak() - o2.getBoneBreak();
            }
        });
        queue.enQueue(new Person1("Jack", 2));
        queue.enQueue(new Person1("Rose", 10));
        queue.enQueue(new Person1("Jake", 5));
        queue.enQueue(new Person1("James", 15));


        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }



}
