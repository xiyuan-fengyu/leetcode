package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 10:13.
 * https://leetcode-cn.com/contest/biweekly-contest-6/problems/minimum-swaps-to-group-all-1s-together/
 */
@EnableTemplateString
public class _1151_M {

    class Solution {
        public int minSwaps(int[] data) {
            int countOfOne = 0;
            for (int i = 0; i < data.length; i++) {
                // 因为 0 <= data[i] <= 1，所以这里可以通过加上 data[i] 来统计 1 出现的次数，下面几处同理
                countOfOne += data[i];
            }
            // 用一个大小为 countOfOne 的窗口从左往右移动，计算窗口内 1 的数量的最大值
            int countOfOneInWindow = 0;
            for (int i = 0; i < countOfOne; i++) {
                countOfOneInWindow += data[i];
            }
            int maxCountOfOneInWindow = countOfOneInWindow;
            for (int i = countOfOne; i < data.length; i++) {
                // 窗口右边新增1
                // 窗口左边移除1
                countOfOneInWindow += data[i] - data[i - countOfOne];
                if (countOfOneInWindow > maxCountOfOneInWindow) {
                    maxCountOfOneInWindow = countOfOneInWindow;
                }
            }
            // 将其他 1 移动到含 1 最多的窗口，即为最少移动次数
            return countOfOne - maxCountOfOneInWindow;
        }
    }

    public static void main(String[] args) {
        Tester.test(r(/*
        ["Solution", "minSwaps", "minSwaps", "minSwaps"]
        [[], [1,0,1,0,1], [0,0,0,1,0], [1,0,1,0,1,0,0,1,1,0,1]]
        [null, 1, 0, 3]
        */));
    }

}