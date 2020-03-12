package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/11 14:30.
 */
@EnableTemplateString
public class _I_1614_M {

    /*
https://leetcode-cn.com/problems/best-line-lcci/
面试题 16.14. 最佳直线
给定一个二维平面及平面上的 N 个点列表Points，其中第i个点的坐标为Points[i]=[Xi,Yi]。请找出一条直线，其通过的点的数目最多。

设穿过最多点的直线所穿过的全部点编号从小到大排序的列表为S，你仅需返回[S[0],S[1]]作为答案，若有多条直线穿过了相同数量的点，则选择S[0]值较小的直线返回，S[0]相同则选择S[1]值较小的直线返回。

示例：

输入： [[0,0],[1,1],[1,0],[2,0]]
输出： [0,2]
解释： 所求直线穿过的3个点的编号为[0,2,3]
提示：

2 <= len(Points) <= 300
len(Points[i]) = 2
     */

    // 通过直线方程表达式作为key
    class Solution_v1 {

        class LinePoints {
            int[] minPointIndexes;
            Set<Integer> points;
            LinePoints() {
                points = new HashSet<>();
            }
            LinePoints(int index1, int index2) {
                minPointIndexes = new int[] {index1, index2};
                points = new HashSet<>();
                points.add(index1);
                points.add(index2);
            }
        }

        private int greatestCommonDivisor(int num1, int num2) {
            int temp;
            int res = num2;
            while((temp = num1 % num2) != 0) {
                res = temp;
                num1 = num2;
                num2 = res;
            }
            return res;
        }

        private String lineExp(int[] point1, int[] point2) {
            if (point1[0] == point2[0]) {
                return "x=" + point1[0];
            }
            else {
                if (point2[1] == point1[1]) {
                    return "y=" + point1[1];
                }
                else {
                    int xD = point2[0] - point1[0];
                    int yD = point2[1] - point1[1];
                    int gcd = greatestCommonDivisor(xD, yD);
                    xD /= gcd;
                    yD /= gcd;
                    int x0 = point1[0] / xD * xD - point1[0];
                    int y0 = point1[0] / xD * yD - point1[1];
                    return "y" + (y0 >= 0 ? "+" : "") + y0 + "=" + yD + "/" + (xD < 0 ? "(" + xD + ")" : xD) + "*(x" + (x0 >= 0 ? "+" : "") + x0 + ")";
                }
            }
        }

        public int[] bestLine(int[][] points) {
            // [x, y, pointCount]
            Map<String, LinePoints> lineCounts = new HashMap<>();
            for (int i = 0; i < points.length - 1; i++) {
                int[] point1 = points[i];
                for (int j = i + 1; j < points.length; j++) {
                    int[] point2 = points[j];
                    String line = lineExp(point1, point2);
                    LinePoints linePoints = lineCounts.get(line);
                    if (linePoints == null) {
                        lineCounts.put(line, new LinePoints(i, j));
                    }
                    else {
                        linePoints.points.add(i);
                        linePoints.points.add(j);
                    }
                }
            }
            LinePoints maxLinePoints = new LinePoints();
            for (Map.Entry<String, LinePoints> entry : lineCounts.entrySet()) {
                LinePoints linePoints = entry.getValue();
                int compareRes = linePoints.points.size() != maxLinePoints.points.size() ? -(linePoints.points.size() - maxLinePoints.points.size()) :
                        linePoints.minPointIndexes[0] != maxLinePoints.minPointIndexes[0] ? linePoints.minPointIndexes[0] - maxLinePoints.minPointIndexes[0] :
                                linePoints.minPointIndexes[1] - maxLinePoints.minPointIndexes[1];
                if (compareRes < 0) {
//                    System.out.println(String.format("%s %d_%d %d", entry.getKey(), linePoints.minPointIndexes[0], linePoints.minPointIndexes[1], linePoints.points.size()));
                    maxLinePoints = linePoints;
                }
            }
            return maxLinePoints.minPointIndexes;
        }
    }

    // 两个点构成一条直线，然后检测后面的点是否在这条线上
    class Solution {
        public int[] bestLine(int[][] points) {
            int[] best = {0, 1};
            int maxPointNum = 2;
            int samePointNum;
            // Map<Double, int[firstPointIndex, secondPointIndex, count]>
            Map<Double, int[]> lines = new HashMap<>();
            for (int i = 0; i < points.length - maxPointNum; i++) {
                samePointNum = 1;
                lines.clear();
                int[] firstP = points[i];
                int localMaxNum = 0;
                double localMaxK = Double.NaN;
                for (int j = i + 1; j < points.length; j++) {
                    int[] secondP = points[j];
                    int xd = secondP[0] - firstP[0];
                    int yd = secondP[1] - firstP[1];
                    double k;
                    if (xd == 0) {
                        if (yd == 0) {
                            samePointNum++;
                            continue;
                        }
                        else {
                            k = Double.POSITIVE_INFINITY;
                        }
                    }
                    else if (yd == 0) {
                        k = 0;
                    }
                    else {
                        k = yd / (double) xd;
                    }
                    int[] line = lines.get(k);
                    if (line == null) {
                        lines.put(k, line = new int[] {i, j, 0});
                    }
                    line[2]++;
                    if (localMaxNum < line[2]) {
                        localMaxNum = line[2];
                        localMaxK = k;
                    }
                }
                if (maxPointNum < localMaxNum + samePointNum) {
                    maxPointNum = localMaxNum + samePointNum;
                    if (Double.isNaN(localMaxK)) {
                        best[0] = i;
                        best[1] = i + 1;
                    }
                    else {
                        int[] line = lines.get(localMaxK);
                        best[0] = line[0];
                        best[1] = line[1];
                    }
                }
            }
            return best;
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "bestLine", "bestLine", "bestLine", "bestLine", "bestLine"]
                [[], [[0, 0], [0,0], [0,0]], [[[0,0],[1,1],[1,0],[2,0]]], [[-2160,34152],[-2942,1637],[-8995,-31970],[-15048,-33786],[-18996,-41685],[-24537,-19974],[-40199,14156],[-16766,-24497],[3111,-28338],[-16766,-25972],[15217,-24706],[-2942,-30154],[21270,-22890],[-15361,-15384]], [[-13260,8589],[1350,8721],[-37222,-19547],[-54293,-29302],[-10489,-13241],[-19382,574],[5561,1033],[-22508,-13241],[-1542,20695],[9277,2820],[-32081,16145],[-50902,23701],[-8636,19504],[-17042,-28765],[-27132,-24156],[-48323,-4607],[30279,29922]], [[26231,-25884],[-19605,-32229],[-42142,6967],[-32350,-5954],[-12288,-34936],[-25045,-17834],[-22123,-22586],[-11179,10225],[-42142,26259],[-20175,-25754],[-27480,-13874],[-35469,1968],[-33662,10062],[-18227,-28922],[1474,-8497],[-19254,6208],[-25125,32761],[-24558,-18626],[-21536,11834],[-26739,2024],[-11110,-12119],[-42142,22356],[-4424,-19828],[-36497,19791],[-21221,-19913],[-83,-28569],[-28796,-10309],[-42142,29221],[-23953,28917],[-2458,-16051],[-12565,14308],[3863,1790],[-24071,-19418],[-1241,-33095],[7372,2834],[-40591,-17272],[-10322,-31159],[-43474,11378],[-9277,6550],[-18593,28531],[-42142,14245],[-21180,-28558],[-28941,-11498],[21134,29273],[-30696,-5620],[-1605,-11552],[-7633,2671],[-42142,32761],[-42142,21439],[-33780,810],[-16702,-22332],[9449,-21091],[-17740,-29714],[-40837,-28274],[-8441,-14658],[-11469,11722],[11304,10388],[-29520,28204],[-42142,22209],[-42142,45644],[-15305,20773],[-23276,6848],[-27967,-13082],[-25923,-13208],[-28454,-12290],[-26993,-14666],[3440,-4720],[-42142,23953],[-42142,28965],[-19688,-26546],[-11906,13881],[-7538,-59972],[-41882,-40622],[-29915,-9914],[19662,16384]] ]
                [null, [0,1], [0, 2], [2, 3], [0, 6],[3,5] ]
                */)
        );
    }

}
