package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/14 9:42.
 * https://leetcode-cn.com/problems/ordinal-number-of-date/submissions/
 * 动态规划 DP
 */
@EnableTemplateString
public class _1155_M {

    private static class Solution {

        public int numRollsToTarget(int d, int f, int target) {
            if (d == 1) {
                return target <= f ? 1: 0;
            }
            else if (d > target || d * f < target) {
                return 0;
            }
            else if (d == target || d * f == target) {
                return 1;
            }

            int[] lastCache = new int[target + 1];
            Arrays.fill(lastCache, 1, Math.min(target, f) + 1, 1);

            int mod = (int) (Math.pow(10, 9) + 7);
            int[] curCache = new int[target + 1];
            for (int i = 2; i <= d; i++) {
                // 因为在上一次迭代计算完成后，将 lastCache 的引用赋值给了 curCache，所以 curCache[i - 2], curCache[i - 1] 两项需要手动置零
                curCache[i - 2] = curCache[i - 1] = 0;
                for (int j = i, maxJ = Math.min(target, i * f); j <= maxJ; j++) {
                    // f(d, f, target) = sum(f(d - 1, f, target - k)), 1 <= k <= f
                    // curCache 的计算方式，在 lastCache 上从左往右平移一个大小为f的窗口，计算窗口内数字的和
                    // curCache[j] = lastCache[j - 1 -f + 1] + lastCache[j - 1 -f + 1] + ... + lastCache[j - 1]
                    //             = (lastCache[j - 1 -f] + ... + lastCache[j - 1 - 1]) + lastCache[j - 1] - lastCache[j - 1 -f]
                    //             = curCache[j - 1] + lastCache[j - 1] - lastCache[j - 1 - f]
                    curCache[j] = (lastCache[j - 1] + curCache[j - 1] - (j - 1 - f >= 0 ? lastCache[j - 1 - f] : 0)) % mod;
                    if (curCache[j] < 0) {
                        curCache[j] += mod;
                    }
                }
                // 一次迭代计算完成，交换 lastCache, curCache 引用
                int[] temp = lastCache;
                lastCache = curCache;
                curCache = temp;
            }
            return lastCache[target];
        }

    }

    public static void main(String[] args) {
        Tester.test(r(/*
        ["Solution","numRollsToTarget","numRollsToTarget","numRollsToTarget","numRollsToTarget","numRollsToTarget"]
        [[],[1, 6, 3],[2, 6, 7],[2, 5, 10],[1, 2, 3],[30, 30, 500]]
        [null,1,6,1,0,222616187]
        */));
    }

}
