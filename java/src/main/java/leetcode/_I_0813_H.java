package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/10 10:05.
 */
@EnableTemplateString
public class _I_0813_H {

    /*
https://leetcode-cn.com/problems/pile-box-lcci/
面试题 08.13. 堆箱子
堆箱子。给你一堆n个箱子，箱子宽 wi、高hi、深di。箱子不能翻转，将箱子堆起来时，下面箱子的宽度、高度和深度必须大于上面的箱子。实现一种方法，搭出最高的一堆箱子。箱堆的高度为每个箱子高度的总和。

输入使用数组[wi, di, hi]表示每个箱子。

示例1:

 输入：box = [[1, 1, 1], [2, 2, 2], [3, 3, 3]]
 输出：6
示例2:

 输入：box = [[1, 1, 1], [2, 3, 4], [2, 6, 7], [3, 4, 5]]
 输出：10
提示:

箱子的数目不大于3000个。
     */

    /**
     * 暴力递归，超时
     */
    class Solution_v1 {

        public int pileBox(int[][] boxes) {
            Arrays.sort(boxes, (box1, box2) -> {
                if (box1[0] != box2[0]) {
                    return box1[0] < box2[0] ? -1 : 1;
                }
                else if (box1[1] != box2[1]) {
                    return box1[1] < box2[1] ? -1 : 1;
                }
                else if (box1[2] != box2[2]) {
                    return box1[2] < box2[2] ? -1 : 1;
                }
                return 0;
            });
            return _pileBox(boxes, 0, -1);
        }

        private boolean smaller(int[] box1, int[] box2) {
            return box1 == null ||
                    (box1[0] < box2[0]
                    && box1[1] < box2[1]
                    && box1[2] < box2[2]);
        }

        private int _pileBox(int[][] boxes, int from, int prev) {
            int maxH = 0;
            int[] prevBox = prev == -1 ? null : boxes[prev];
            for (int i = from; i < boxes.length; i++) {
                if (smaller(prevBox, boxes[i])) {
                    maxH = Math.max(maxH, boxes[i][2] + _pileBox(boxes, i + 1, i));
                }
                maxH = Math.max(maxH, _pileBox(boxes, i + 1, -1));
            }
            return maxH;
        }

    }

    class Solution {

        public int pileBox(int[][] boxes) {
            int maxH = 0;
            if (boxes.length > 0) {
                Arrays.sort(boxes, (box1, box2) -> box1[0] != box2[0] ? box1[0] - box2[0] :
                        box1[1] != box2[1] ? box1[1] - box2[1] : box1[2] - box2[2]);
                int[] cache = new int[boxes.length];
                maxH = cache[0] = boxes[0][2];
                for (int i = 1; i < boxes.length ; i++) {
                    int localMaxH = 0;
                    int[] box = boxes[i];
                    for (int j = i - 1; j > -1; j--) {
                        int[] prevBox = boxes[j];
                        if (prevBox[0] < box[0]
                                && prevBox[1] < box[1]
                                && prevBox[2] < box[2]) {
                            if (localMaxH < cache[j]) {
                                localMaxH = cache[j];
                            }
                        }
                    }
                    if (maxH < (cache[i] = localMaxH + box[2])) {
                        maxH = cache[i];
                    }
                }
            }
            return maxH;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "pileBox", "pileBox"]
                [[], [[[1, 1, 1], [2, 2, 2], [3, 3, 3]]], [[[1, 1, 1], [2, 3, 4], [2, 6, 7], [3, 4, 5]]]]
                [null, 6, 10]
                */)
        );
    }

}
