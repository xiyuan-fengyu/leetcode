package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/14 9:42.
 * https://leetcode-cn.com/problems/number-of-dice-rolls-with-target-sum/
 * 动态规划 DP
 */
@EnableTemplateString
public class _1155_M {

    /*
    1155. 掷骰子的N种方法
    这里有 d 个一样的骰子，每个骰子上都有 f 个面，分别标号为 1, 2, ..., f。
    我们约定：掷骰子的得到总点数为各骰子面朝上的数字的总和。
    如果需要掷出的总点数为 target，请你计算出有多少种不同的组合情况（所有的组合情况总共有 f^d 种），模 10^9 + 7 后返回。

    示例 1：
    输入：d = 1, f = 6, target = 3
    输出：1

    示例 2：
    输入：d = 2, f = 6, target = 7
    输出：6

    示例 3：
    输入：d = 2, f = 5, target = 10
    输出：1

    示例 4：
    输入：d = 1, f = 2, target = 3
    输出：0

    示例 5：
    输入：d = 30, f = 30, target = 500
    输出：222616187

    提示：
    1 <= d, f <= 30
    1 <= target <= 1000
     */

    class Solution {

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
                // 因为在上一次迭代计算完成后，将 lastCache 的引用赋值给了 curCache
                // 在本此迭代中curCache[i - 2], curCache[i - 1] 两项应该是0（因为 i 个骰子不可能投出总和小于 i 的情况），不置0，会导致本此迭代计算结果错误
                // i - 3 以及左边的项一定是 0 ，不用费力去置 0
                // i 及右边的项正是本此迭代计算过程中要计算的，不置 0 ，也不会影响本此迭代计算
                curCache[i - 2] = curCache[i - 1] = 0;
                for (int j = i, maxJ = Math.min(target, i * f); j <= maxJ; j++) {
                    // F(d, f, target) = sum(F(d - 1, f, target - k)), 1 <= k <= f
                    // curCache 的计算方式，在 lastCache 上从左往右平移一个大小为f的窗口，计算窗口内数字的和
                    // curCache[j] = lastCache[j - 1 - f + 1] + lastCache[j - 1 - f + 1] + ... + lastCache[j - 1]
                    //             = (lastCache[j - 1 - f] + ... + lastCache[j - 1 - 1]) + lastCache[j - 1] - lastCache[j - 1 - f]
                    //             = curCache[j - 1] + lastCache[j - 1] - lastCache[j - 1 - f]
                    curCache[j] = (curCache[j - 1] + lastCache[j - 1] - (j - 1 - f >= 0 ? lastCache[j - 1 - f] : 0)) % mod;
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
