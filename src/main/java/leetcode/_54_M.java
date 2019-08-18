package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.ArrayList;
import java.util.List;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/18 10:01.
 * https://leetcode-cn.com/problems/spiral-matrix/
 */
@EnableTemplateString
public class _54_M {

    /*
    54. 螺旋矩阵
    给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。

    示例 1:
    输入:
    [
     [ 1, 2, 3 ],
     [ 4, 5, 6 ],
     [ 7, 8, 9 ]
    ]
    输出: [1,2,3,6,9,8,7,4,5]

    示例 2:
    输入:
    [
      [1, 2, 3, 4],
      [5, 6, 7, 8],
      [9,10,11,12]
    ]
    输出: [1,2,3,4,8,12,11,10,9,5,6,7]
     */

    class Solution {

        public List<Integer> spiralOrder(int[][] matrix) {
            int row = matrix.length;
            int col = row > 0 ? matrix[0].length : 0;
            List<Integer> res = new ArrayList<>(row * col);
            if (row > 0 && col > 0) {
                int mid = (row <= col ? row - 1 : col - 1) >> 1;
                int top = 0;
                while (top <= mid) {
                    int left = top;
                    int right = col - left - 1;
                    int bottom = row - top - 1;
                    if (top == bottom) {
                        for (int i = left; i <= right; i++) {
                            res.add(matrix[top][i]);
                        }
                    }
                    else if (left == right) {
                        for (int i = top; i <= bottom; i++) {
                            res.add(matrix[i][right]);
                        }
                    }
                    else {
                        for (int i = left; i < right; i++) {
                            res.add(matrix[top][i]);
                        }
                        for (int i = top; i < bottom; i++) {
                            res.add(matrix[i][right]);
                        }
                        for (int i = right; i > left; i--) {
                            res.add(matrix[bottom][i]);
                        }
                        for (int i = bottom; i > top; i--) {
                            res.add(matrix[i][left]);
                        }
                    }
                    top++;
                }
            }
            return res;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "spiralOrder", "spiralOrder", "spiralOrder"]
                [[], [ [ 1, 2, 3 ], [ 4, 5, 6 ], [ 7, 8, 9 ] ], [ [1, 2, 3, 4], [5, 6, 7, 8], [9,10,11,12] ], [[1],[2],[3],[4],[5],[6],[7],[8],[9],[10]]]
                [null, [1,2,3,6,9,8,7,4,5], [1,2,3,4,8,12,11,10,9,5,6,7], [1,2,3,4,5,6,7,8,9,10]]
                */)
        );
    }

}
