package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/4/14 17:09.
 */
@EnableTemplateString
public class _I_1719_H {

    /*
https://leetcode-cn.com/problems/missing-two-lcci/
面试题 17.19. 消失的两个数字
给定一个数组，包含从 1 到 N 所有的整数，但其中缺了两个数字。你能在 O(N) 时间内只用 O(1) 的空间找到它们吗？

以任意顺序返回这两个数字均可。

示例 1:

输入: [1]
输出: [2,3]
示例 2:

输入: [2,3]
输出: [1,4]
提示：

nums.length <= 30000
     */

    /*
    1~n 个数的和为 sum = (1 + n) * n / 2
    而如果缺少两个数a和b，设其他数和为 sumWithoutAB， 则必有 sumAB = a + b = sum - sumWithoutAB
    取 halfAB = sumAB / 2，必有 a <= halfAB 或者 b <= halfAB，
    再次扫描一次数组，计算小于等于 halfAB 的数字的和，记为 sum1，
    则其中缺少的一个数 a = (1 + halfAB) * halfAB / 2 - sum1，
    另外一个数 b = sumAB - a
     */
    class Solution_v1 {

        public int[] missingTwo(int[] nums) {
            int n = nums.length + 2;
            int sum = (1 + n) * n / 2;
            int sumWithoutAB = 0;
            for (int num : nums) {
                sumWithoutAB += num;
            }
            int sumAB = sum - sumWithoutAB;
            int halfAB = sumAB / 2;
            int sum1 = 0;
            for (int num : nums) {
                if (num <= halfAB) {
                    sum1 += num;
                }
            }
            int a = (1 + halfAB) * halfAB / 2 - sum1;
            return new int[] {a, sumAB - a};
        }

    }

    /*
    这道题稍微转换一下，就和“一数组中只出现一次的数”一样了
    看成 [1:N] 和 nums 组成
    比如：[1] + [1,2,3]，其中缺少的 2，3 只会出现一次
    因为 a ^ a = 0, a ^ 0 = a
    将 [1] + [1,2,3] 所有结果都异或起来，最终结果为缺少的两个数的异或
     */
    class Solution {

        public int[] missingTwo(int[] nums) {
            int n = nums.length + 2;
            int sum = 0;
            for (int num : nums) {
                sum ^= num;
            }
            for (int i = 1; i <= n; i++) {
                sum ^= i;
            }
            /*
            -sum 获得补码， sum & (-sum) 获得sum二进制中最右边的1
            参考代码
            int num = 998;
            System.out.println(int2Binary(num));
            System.out.println(int2Binary(-num));
            System.out.println(int2Binary(num & (-num)));
             */
            int diff = sum & (-sum);
            int x = 0;
            for(int num : nums) {
//                System.out.println(String.format("         num=%s\n(num & diff)=%s\n", int2Binary(num), int2Binary(num & diff)));
                if ((num & diff) != 0) {
                    x ^= num;
                }
            }
            for(int i = 1; i <= n; i++) {
//                System.out.println(String.format("         i=%s\n(i & diff)=%s\n", int2Binary(i), int2Binary(i & diff)));
                if ((i & diff) != 0) {
                    x ^= i;
                }
            }
            x = Math.min(x, x ^ sum);
            return new int[] {x, x ^ sum};
        }

    }

    private static String int2Binary(int num) {
        String str = Integer.toBinaryString(num);
        while (str.length() < 32) {
            str = "0" + str;
        }
        return str;
    }

    public static void main(String[] args) {
        int num = 998;
        System.out.println(int2Binary(num));
        System.out.println(int2Binary(-num));
        System.out.println(int2Binary(num & (-num)));
        Tester.test(
                r(/*
                ["Solution", "missingTwo"]
                [[], [1] ]
                [null, [2, 3] ]
                */)
        );
    }

}
