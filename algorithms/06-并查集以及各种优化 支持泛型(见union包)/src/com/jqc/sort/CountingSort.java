package com.jqc.sort;

/**
 * @author appbanana
 * 计数排序
 * @date 2019/12/26
 */
public class CountingSort extends Sort<Integer> {
    @Override
    protected void sort() {
//         sort0();
        // sort0的改进版
        sort1();
//        sort2();
    }

    private void sort0() {
        // 先找出最大值
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 开辟内存空间
        int[] counts = new int[max + 1];
        // 统计元素出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        }

        int index = 0;
        // 遍历元素 时间复杂度为O(n)
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                array[index++] = i;
            }
        }
    }

    /**
     * sort0的改进版 节省内存空间 可以是负整数
     */
    private void sort1() {
        // 找出最大值, 最小值  目的是为了获取下面数组的内存(max - min + 1)个内存空间
        // 实际上就是所有元素相对最小值的相对位置
        int max = array[0];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        // 开辟内存空间 存储元素出现的次数
        int[] counts = new int[max - min + 1];
        // 统计元素出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        // 累加次数 目的是为了知道该元素的位置，
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从数组后面往前遍历 倒着遍历的好处就是稳定排序
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] - min]] = array[i];
        }
        // 将有序数组赋值给原数组
        for (int i = 0; i < array.length; i++) {
            array[i] = newArray[i];
        }
    }

    /**
     * 这个方法是我自己搞的， 尽量使用sort1方法，sort1兼顾了下面的两个缺陷
     * 缺点：
     * ① 无法用于自定义类型，只能用于数字类型
     * ② 没有考虑稳定性
     */
    private void sort2() {
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }

        int[] countArr = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            countArr[array[i] - min]++;
        }
        int index = 0;
        for (int i = 0; i < countArr.length; i++) {
            while (countArr[i]-- > 0) {
                array[index++] = min + i;
            }
        }
    }

    /**
     * 针对自定义对象测试
     * @param args
     */
    public static void main(String[] args) {
        Person[] persons = new Person[] {
                new Person(20, "A"),
                new Person(-13, "B"),
                new Person(17, "C"),
                new Person(12, "D"),
                new Person(-13, "E"),
                new Person(20, "F")
        };

        // 找出最大值, 最小值  目的是为了获取下面数组的内存(max - min + 1)个内存空间
        int max = persons[0].age;
        int min = persons[0].age;
        for (int i = 0; i < persons.length; i++) {
            if (persons[i].age > max) {
                max = persons[i].age;
            }
            if (persons[i].age < min) {
                min = persons[i].age;
            }
        }
        // 开辟内存空间 存储元素出现的次数
        int[] counts = new int[max - min + 1];
        // 统计元素出现的次数
        for (int i = 0; i < persons.length; i++) {
            counts[persons[i].age - min]++;
        }
        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从数组后面往前遍历 倒着遍历的好处就是稳定排序
        Person[] newArray = new Person[persons.length];
        for (int i = persons.length - 1; i >= 0; i--) {
            newArray[--counts[persons[i].age - min]] = persons[i];
        }
        // 将有序数组赋值给原数组
        for (int i = 0; i < persons.length; i++) {
            persons[i] = newArray[i];
        }
    }
    /**
     * 私有类 测试自定义对象的计数排序
     */
    private static class Person {
        int age;
        String name;
        Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person [age=" + age
                    + ", name=" + name + "]";
        }
    }

}
