package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/11 11:25.
 */
@EnableTemplateString
public class _I_1607_E {

    /*
https://leetcode-cn.com/problems/maximum-lcci/
面试题 16.07. 最大数值
编写一个方法，找出两个数字a和b中最大的那一个。不得使用if-else或其他比较运算符。

示例：

输入： a = 1, b = 2
输出： 2
     */

    class Solution {
        /*
        思路一：
        max = ((a + b) + abs(a - b)) / 2
        min = ((a + b) - abs(a - b)) / 2

        思路二：
        通过 a - b 的符号来判定谁大
         */
        public int maximum(int a, int b) {
            long sub = (long) a - b;
            // sub >= 0 => sign = 0; sub < 0 => sign = -1
            long sign = sub >> 63;
            return (int) ((1 + sign) * a + (-sign) * b);
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "maximum"]
                [[], [2147483647, -2147483648]]
                [null, 2147483647]
                */)
        );
    }

}
