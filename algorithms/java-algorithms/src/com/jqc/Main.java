package com.jqc;
import com.jqc.tools.Asserts;
import com.jqc.tools.Times;
import com.jqc.union.*;


public class Main {
    static final int count = 10_0000;
//    static final int count = 100;

    public static void main(String[] args) {
        testUnionFind(new UnionFind_QF(count));
        testUnionFind(new UnionFind_QU(count));
        testUnionFind(new UnionFind_QU_S(count));
        testUnionFind(new UnionFind_QU_R(count));
//        testUnionFind(new UnionFind_QU_R_PC(count));  // 这个太慢了
        testUnionFind(new UnionFind_QU_R_PS(count));
        testUnionFind(new UnionFind_QU_R_PH(count));
    }

    /**
     * 测试并查集 测试数据详见img/01-union_find.jpg图片
     * @param uf
     */
    static void testUnionFind(UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 9);
        uf.union(8, 10);
        uf.union(9, 11);

        Asserts.test(!uf.isSame(2, 7));

        uf.union(2, 7);
        Asserts.test(uf.isSame(2, 7));

        Times.test(uf.getClass().getSimpleName(), ()-> {
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }
        });

    }

}
