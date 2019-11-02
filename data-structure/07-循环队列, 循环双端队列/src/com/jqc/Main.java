package com.jqc;

import com.jqc.circle.CircleDeque;
import com.jqc.circle.CircleQueue;
import com.jqc.list.Deque;
import com.jqc.list.Queue;
import com.jqc.list.Stack;

public class Main {

    public static void main(String[] args) {
        // 测试栈, 队列, 双端队列
//        test1();
//        System.out.println("=====================");
//        test2();
//        System.out.println("=====================");
//        test3();
//
        // 测试循环队列
        test4();
        // 测试循环双端队列
        test5();
    }
    static void test1() {
        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    static void test2() {

        Queue<Integer> queue = new Queue<>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    static void test3() {

        Deque<Integer> queue = new Deque<>();
        queue.enQueueFront(11);
        queue.enQueueFront(22);
        queue.enQueueRear(33);
        queue.enQueueRear(44);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueFront());
        }
    }

    static void test4() {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        // 0 1 2 3 4 5 6 7 8 9
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        // null null null null null 5 6 7 8 9
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        // 15 16 17 18 19 5 6 7 8 9
        for (int i = 15; i < 20; i++) {
            queue.enQueue(i);
        }

        System.out.println(queue);

        for (int i = 20; i < 25; i++) {
            queue.enQueue(i);
        }
//        while (!queue.isEmpty()) {
//            System.out.println(queue.deQueue());
//        }
        System.out.println(queue);
        queue.clear();
        System.out.println(queue);

    }

    static void test5() {

        CircleDeque<Integer> queue = new CircleDeque<Integer>();
        // 头5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 尾

        // 头 8 7 6  5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 尾
        for (int i = 0; i < 10; i++) {
            queue.enQueueFront(i + 1);
            queue.enQueueRear(i + 100);
        }

        System.out.println(queue);
        System.out.println("--------------------------------------");

        // 头 null 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null null 尾
        for (int i = 0; i < 3; i++) {
            queue.deQueueFront();
            queue.deQueueRear();
        }

        System.out.println(queue);
        System.out.println("--------------------------------------");

        // 头 11 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null 12 尾
        queue.enQueueFront(11);
        queue.enQueueFront(12);
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueFront());
        }
    }


}
