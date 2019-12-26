package com.jqc.sort;

import com.jqc.tools.Asserts;
import com.jqc.tools.Integers;

import java.util.Arrays;
/**
 * @author appbanana
 * 测试十大排序算法
 * @date 2019/12/26
 */
public class TestSort {

    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 0, 10000);

//        Integer[] array1 = {1, 5, 18, 9, 10, 12};
////        System.out.println(BinarySearch.indexOf(array1, 11));
//        ShellSort<Integer> shellSort = new ShellSort<>();
//        shellSort.sort(array1);

//        Integer[] array1 = {1, 5, 18, 9, 100, 12};
//        RadixSort sort2 = new RadixSort();
//        sort2.sort(array1);
//        System.out.println(Arrays.toString(array1));


        testSort(array,
                new BubbleSort3<Integer>(),
                new SelectionSort<Integer>(),
                new HeapSort<Integer>(),
                new InsertionSort3<Integer>(),
                new QuickSort<Integer>(),
                new MergeSort<Integer>(),
                new CountingSort(),
                new RadixSort()
        );

    }

    static void testSort(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);
        for (Sort sort : sorts) {
            System.out.println(sort);
        }

    }
}
