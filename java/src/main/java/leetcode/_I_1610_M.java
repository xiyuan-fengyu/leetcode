package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/11 13:29.
 */
@EnableTemplateString
public class _I_1610_M {

    /*
https://leetcode-cn.com/problems/living-people-lcci/
面试题 16.10. 生存人数
给定N个人的出生年份和死亡年份，第i个人的出生年份为birth[i]，死亡年份为death[i]，实现一个方法以计算生存人数最多的年份。

你可以假设所有人都出生于1900年至2000年（含1900和2000）之间。如果一个人在某一年的任意时期都处于生存状态，那么他们应该被纳入那一年的统计中。例如，生于1908年、死于1909年的人应当被列入1908年和1909年的计数。

如果有多个年份生存人数相同且均为最大值，输出其中最小的年份。

示例：

输入：
birth = {1900, 1901, 1950}
death = {1948, 1951, 2000}
输出： 1901
提示：

0 < birth.length == death.length <= 10000
birth[i] <= death[i]
     */

    // 该方法在计算大区间的时候有优势
    class Solution_v1 {
        public int maxAliveYear(int[] birth, int[] death) {
            int[][] edges = new int[birth.length * 2][];
            for (int i = 0; i < birth.length; i++) {
                edges[i * 2] = new int[] {birth[i], 0};
                edges[i * 2 + 1] = new int[] {death[i], 1};
            }
            Arrays.sort(edges, (e1, e2) -> e1[0] != e2[0] ? e1[0] - e2[0] : e1[1] - e2[1]);
            int maxAlive = 0;
            int maxAliveYear = 0;
            int curAlive = 0;
            for (int[] edge : edges) {
                if (edge[1] == 1) {
                    curAlive--;
                } else {
                    curAlive++;
                    if (maxAlive < curAlive) {
                        maxAlive = curAlive;
                        maxAliveYear = edge[0];
                    }
                }
            }
            return maxAliveYear;
        }
    }

    // 统计 1900~2000年之间 101 年的出生死亡变化情况
    class Solution {
        public int maxAliveYear(int[] birth, int[] death) {
            int[] changes = new int[102];
            for (int i = 0; i < birth.length; i++) {
                changes[birth[i] - 1900]++;
                // 死亡当年也记为存活，下一年存活人数才减1
                changes[death[i] - 1900 + 1]--;
            }

            int curAlive = 0;
            int maxAlive = 0;
            int maxAliveYear = 1990;
            for (int i = 0; i < changes.length; i++) {
                curAlive += changes[i];
                if (curAlive > maxAlive) {
                    maxAlive = curAlive;
                    maxAliveYear = 1900 + i;
                }
            }
            return maxAliveYear;
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "maxAliveYear"]
                [[], [[1900, 1901, 1950], [1948, 1951, 2000]]]
                [null, 1901]
                */)
        );
    }

}
