package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/13 17:21.
 */
@EnableTemplateString
public class _I_1718_M {

    /*
https://leetcode-cn.com/problems/shortest-supersequence-lcci/
面试题 17.18. 最短超串
假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。

返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组。

示例 1:

输入:
big = [7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7]
small = [1,5,9]
输出: [7,10]
示例 2:

输入:
big = [1,2,3]
small = [4]
输出: []
提示：

big.length <= 100000
1 <= small.length <= 100000
     */

    class Solution {
        public int[] shortestSeq(int[] big, int[] small) {
            int[] minSeq = {};
            if (big.length < small.length) {
                return minSeq;
            }

            HashMap<Integer, Integer> wantedCounts = new HashMap<>();
            for (int i : small) {
                wantedCounts.put(i, 0);
            }

            int minSeqLen = big.length + 1;
            int left = 0;
            int right = 0;
            int contains = 0;
            while (right < big.length) {
                while (right < big.length && contains < small.length) {
                    int count = wantedCounts.getOrDefault(big[right], -1);
                    if (count > -1) {
                        if (count == 0) {
                            contains++;
                        }
                        wantedCounts.put(big[right], count + 1);
                    }
                    right++;
                }
                if (contains == small.length) {
                    while (left < right) {
                        int count = wantedCounts.getOrDefault(big[left], -1);
                        if (count > -1) {
                            if (contains + 1 == small.length && count == 1) {
                                break;
                            }
                            else if (count == 1) {
                                if (minSeqLen > right - left) {
                                    minSeqLen = right - left;
                                    minSeq = new int[] {left, right - 1};
                                }
                                contains--;
                            }
                            wantedCounts.put(big[left], count - 1);
                        }
                        left++;
                    }
                }
            }
            return minSeq;
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "shortestSeq", "shortestSeq", "shortestSeq", "shortestSeq"]
                [[], [[7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7], [1,5,9]], [[1, 2, 3], [1, 2, 3]], [[521704, 897261, 279103, 381783, 668374, 934085, 254258, 726184, 496153, 804155], [897261, 934085, 381783, 496153]], [[842, 336, 777, 112, 789, 801, 922, 874, 634, 121, 390, 614, 179, 565, 740, 672, 624, 130, 555, 714, 9, 950, 887, 375, 312, 862, 304, 121, 360, 579, 937, 148, 614, 885, 836, 842, 505, 187, 210, 536, 763, 880, 652, 64, 272, 675, 33, 341, 101, 673, 995, 485, 16, 434, 540, 284, 567, 821, 994, 174, 634, 597, 919, 547, 340, 2, 512, 433, 323, 895, 965, 225, 702, 387, 632, 898, 297, 351, 936, 431, 468, 694, 287, 671, 190, 496, 80, 110, 491, 365, 504, 681, 672, 825, 277, 138, 778, 851, 732, 176], [950, 885, 702, 101, 312, 652, 555, 936, 842, 33, 634, 851, 174, 210, 287, 672, 994, 614, 732, 919, 504, 778, 340, 694, 880, 110, 777, 836, 365, 375, 536, 547, 887, 272, 995, 121, 225, 112, 740, 567, 898, 390, 579, 505, 351, 937, 825, 323, 874, 138, 512, 671, 297, 179, 277, 304]] ]
                [null, [7,10], [0, 2], [1,8], [2,98] ]
                */)
        );
    }

}
