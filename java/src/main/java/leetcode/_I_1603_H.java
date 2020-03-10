package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/10 14:28.
 */
@EnableTemplateString
public class _I_1603_H {

    /*
https://leetcode-cn.com/problems/intersection-lcci/
面试题 16.03. 交点
给定两条线段（表示为起点start = {X1, Y1}和终点end = {X2, Y2}），如果它们有交点，请计算其交点，没有交点则返回空值。

要求浮点型误差不超过10^-6。若有多个交点（线段重叠）则返回X值最小的点，X坐标相同则返回Y值最小的点。
示例 1：

输入：
line1 = {0, 0}, {1, 0}
line2 = {1, 1}, {0, -1}
输出： {0.5, 0}
示例 2：

输入：
line1 = {0, 0}, {3, 3}
line2 = {1, 1}, {2, 2}
输出： {1, 1}
示例 3：

输入：
line1 = {0, 0}, {1, 1}
line2 = {1, 0}, {2, 1}
输出： {}，两条线段没有交点
提示：

坐标绝对值不会超过2^7
输入的坐标均是有效的二维坐标
     */

    class Solution {

        private int[] sub(int[] v1, int[] v2) {
            return new int[] {v1[0] - v2[0], v1[1] - v2[1]};
        }

        private int crossProduct(int[] v1, int[] v2) {
            return v1[0] * v2[1] - v1[1] * v2[0];
        }

        /*
        公式推导
        https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect/565282#
         */
        public double[] intersection(int[] start1, int[] end1, int[] start2, int[] end2) {
            int[] d1 = sub(end1, start1);
            int[] d2 = sub(end2, start2);
            int[] s1SubS2 = sub(start1, start2);
            int s1SubS2_CP_d1 = crossProduct(s1SubS2, d1);
            int s1SubS2_CP_d2 = crossProduct(s1SubS2, d2);
            int d2_CP_d1 = crossProduct(d2, d1);
            if (d2_CP_d1 != 0) {
                // 两个线段相交 或 延长线相交
                double r1 = s1SubS2_CP_d2 / (double) d2_CP_d1;
                double r2 = s1SubS2_CP_d1 / (double) d2_CP_d1;
                if (0 <= r1 && r1 <= 1 && 0 <= r2 && r2 <= 1) {
                    // 相交
                    return new double[] {
                            start1[0] + r1 * d1[0],
                            start1[1] + r1 * d1[1],
                    };
                }
                else {
                    // 小于0：反向延长相交；大于1：正向延长相交
                    return new double[] {};
                }
            }
            else {
                if (s1SubS2_CP_d1 == 0) {
                    // 共线
                    int[][] points = new int[][] {
                            {start1[0], start1[1], 1},
                            {end1[0], end1[1], 1},
                            {start2[0], start2[1], 2},
                            {end2[0], end2[1], 2},
                    };
                    Arrays.sort(points, (p1, p2) -> p1[0] != p2[0] ? p1[0] - p2[0] : p1[1] != p2[1] ? p1[1] - p2[1] : p1[2] - p2[2]);
                    if (points[0][2] != points[1][2] || (points[1][0] == points[2][0] && points[1][1] == points[2][1])) {
                        return new double[] {points[1][0], points[1][1]};
                    }
                    return new double[] {};
                }
                else {
                    // 平行
                    return new double[] {};
                }
            }
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "intersection", "intersection", "intersection", "intersection"]
                [[], [[0, 0], [1, 0], [1, 1], [0, -1]], [[0, 0], [1, 0], [2, 3], [1, 1]], [[0, 0], [1, 0], [2, 0], [3,0]], [[0, 0], [0, 1], [0, 2], [0,3]]]
                [null, [0.5, 0.0], [], [], []]
                */)
        );
    }

}
