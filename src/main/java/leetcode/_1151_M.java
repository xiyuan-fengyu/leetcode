package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 10:13.
 * https://leetcode-cn.com/contest/biweekly-contest-6/problems/minimum-swaps-to-group-all-1s-together/
 */
@EnableTemplateString
public class _1151_M {

    /*
    1151. 最少交换次数来组合所有的 1
    给出一个二进制数组 data，你需要通过交换位置，将数组中 任何位置 上的 1 组合到一起，并返回所有可能中所需 最少的交换次数。

    示例 1：
    输入：[1,0,1,0,1]
    输出：1
    解释：
    有三种可能的方法可以把所有的 1 组合在一起：
    [1,1,1,0,0]，交换 1 次；
    [0,1,1,1,0]，交换 2 次；
    [0,0,1,1,1]，交换 1 次。
    所以最少的交换次数为 1。

    示例 2：
    输入：[0,0,0,1,0]
    输出：0
    解释：
    由于数组中只有一个 1，所以不需要交换。

    示例 3：
    输入：[1,0,1,0,1,0,0,1,1,0,1]
    输出：3
    解释：
    交换 3 次，一种可行的只用 3 次交换的解决方案是 [0,0,0,0,0,1,1,1,1,1,1]。

    提示：
    1 <= data.length <= 10^5
    0 <= data[i] <= 1
     */

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