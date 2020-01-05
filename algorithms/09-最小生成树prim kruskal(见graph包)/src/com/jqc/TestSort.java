package com.jqc;

import com.jqc.sort.*;
import com.jqc.tools.Asserts;
import com.jqc.tools.Integers;

import java.util.Arrays;

/**
 * @author appbanana
 * @date 2019/12/28
 */
public class TestSort {
    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 0, 10000);

//        Integer[] array1 = {1, 5, 18, 9, 10, 12};
////        System.out.println(BinarySearch.indexOf(array1, 11));
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
