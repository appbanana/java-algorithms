package com.jqc;

import com.jqc.sort.*;
import com.jqc.tools.Asserts;
import com.jqc.tools.Integers;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 0, 10000);

//        testSort(array,
//                new BubbleSort1(),
//                new BubbleSort2(),
//                new BubbleSort3(),
//                new SelectionSort(),
//                new HeapSort()
//        );
        Integer[] array1 = {1, 5, 8, 9, 10, 12};
        System.out.println(BinarySearch.indexOf(array1, 11));

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
