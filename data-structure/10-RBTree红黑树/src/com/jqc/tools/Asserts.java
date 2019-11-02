package com.jqc.tools;

/**
 * @author appbanana
 * @date 2019/10/17
 */
public class Asserts {
    public static void test(boolean value) {
        try {
            if (!value)  throw new Exception("测试未通过");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
