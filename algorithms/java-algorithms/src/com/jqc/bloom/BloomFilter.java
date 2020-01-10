package com.jqc.bloom;

/**
 * 布隆过滤器
 * @author appbanana
 * @date 2020/1/10
 */
public class BloomFilter<T> {

    // 二进制向量的长度(一共有多少个二进制位)
    private int bitSize;

    // 二进制向量
    private long[] bits;

    // 哈希函数的个数
    private int hashSize;

    /**
     * 构造函数
     * @param n 数据规模
     * @param p 误判率 范围（0, 1）
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("参数不合法");
        }

        double ln2 = Math.log(2);
        // 套公式计算该数据规模需要的二进制位长度
        bitSize = (int) (-n * Math.log(p) / (ln2 * ln2));
        // 套公式计算所需要哈希函数的个数
        this.hashSize = (int) (bitSize * ln2 / n);
        // 计算bits数组的长度 计算方法跟计算分页是一样的 pageCount = (n + pageSize - 1) / pageSize
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];

    }

    /**
     *
     * @param value
     * @return 返回值代表该位置是否有发生变化
     */
    public boolean put(T value) {
        valueNullCheck(value);
        // 这是参考google的实现， google是用两个hash函数将value转化成不同的整数，
        // 这里是直接使用是Java的hash生成函数，hash2是偷懒取巧获得的 不用太纠结
        // 简单理解就是利用value生成两个不同的整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        boolean result = false;
        for (int i = 1; i <= hashSize; i++) {
            // 下面这两个不用太纠结， 目的是尽量生成不同的索引值
            int combineHash = hash1 + (i * hash2);
            if (combineHash < 0) {
                combineHash = ~combineHash;
            }
            // hash值对长度取余得到索引
            int index = combineHash % bitSize;
            if (set(index)) result = true;
        }

        return result;
    }
    public boolean contains(T value) {
        valueNullCheck(value);
        //这跟上面一样，简单理解就是利用value生成两个不同的整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            // 下面这两个不用太纠结， 目的是尽量生成不同的索引值
            int combineHash = hash1 + (i * hash2);
            if (combineHash < 0) {
                combineHash = ~combineHash;
            }

            int index = combineHash % bitSize;
            // 如果该索引位置的二进制位为0， 则一定不存在
            if (!get(index)) return false;
        }
        return true;
    }

//    private void set(int index) {
//        long value = bits[index / Long.SIZE];
//        int bitValue = 1<<(index % Long.SIZE);
//        //   101010101010010101
//        // | 000000000000000100   1 << index
//        //   101010111010010101
//        // 将指定位置的值置位1， 同时在把值放到bits数组中
//        bits[index / Long.SIZE] = value | bitValue;
//    }

    private boolean set(int index) {
        long value = bits[index / Long.SIZE];
        int bitValue = 1<<(index % Long.SIZE);
        //   101010101010010101
        // | 000000000000000100   1 << index
        //   101010111010010101
        // 将指定位置的值置位1， 同时在把值放到bits数组中
        bits[index / Long.SIZE] = value | bitValue;
        //(value & bitValue) 获取该为的值 如果为0的话，会置位1，返回true，代表该位置的值发生变化
        // 如果为1，不需要变化， 返回false， 代表该位置的值不需要发生变化
        return (value & bitValue)== 0;
    }
    /**
     * 查看index位置的值
     * @param index
     * @return true 代表为1 false 代表为0
     */
    private boolean get(int index) {
        // bits里面存的是长整形 eg：[long1, long2, long3, ...]
        // index / Long.SIZE 获取该index对应的长整型
        long value = bits[index / Long.SIZE];
        // 左移index % Long.SIZE在&上原来的值， 就可以拿到该位置的二进制位
        return (value & (1<< (index % Long.SIZE))) != 0;
    }

    private void valueNullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value 不能为null");
        }
    }

}
