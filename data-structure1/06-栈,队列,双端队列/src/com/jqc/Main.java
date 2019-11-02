package com.jqc;

import com.jqc.list.Deque;
import com.jqc.list.Queue;
import com.jqc.list.Stack;


public class Main {

    public static void main(String[] args) {
        // 测试栈
        test1();
        // 测试队列
        System.out.println("------------------");
        test2();
        System.out.println("------------------");

        test3();
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



}
