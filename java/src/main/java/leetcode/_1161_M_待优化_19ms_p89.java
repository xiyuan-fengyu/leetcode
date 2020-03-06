package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/18 12:00.
 * https://leetcode-cn.com/contest/weekly-contest-150/problems/maximum-level-sum-of-a-binary-tree/
 */
@EnableTemplateString
public class _1161_M_待优化_19ms_p89 {

    /*
    1161. 最大层内元素和
    给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
    请你找出层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。

    示例：
    ![image](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/08/17/capture.jpeg)
    输入：[1,7,0,7,-8,null,null]
    输出：2
    解释：
    第 1 层各元素之和为 1，
    第 2 层各元素之和为 7 + 0 = 7，
    第 3 层各元素之和为 7 + -8 = -1，
    所以我们返回第 2 层的层号，它的层内元素之和最大。

    提示：
    树中的节点数介于 1 和 10^4 之间
    -10^5 <= node.val <= 10^5
     */

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    class Solution {

        public int maxLevelSum(TreeNode root) {
            List<Integer> levelSums = new ArrayList<>();
            visit(root, 0, levelSums);
            Integer max = null;
            int maxLevel = 1;
            for (int i = 0, size = levelSums.size(); i < size; i++) {
                Integer levelSum = levelSums.get(i);
                if (max == null || max.compareTo(levelSum) < 0) {
                    max = levelSum;
                    maxLevel = i + 1;
                }
            }
            return maxLevel;
        }

        private void visit(TreeNode node, int level, List<Integer> levelSums) {
            while (levelSums.size() <= level) {
                levelSums.add(0);
            }
            levelSums.set(level, levelSums.get(level) + node.val);
            if (node.left != null) {
                visit(node.left, level + 1, levelSums);
            }
            if (node.right != null) {
                visit(node.right, level + 1, levelSums);
            }
        }

    }

    private static TreeNode build(Integer[] xs) {
        TreeNode[] nodes = new TreeNode[xs.length];
        for (int i = 0; i < xs.length; i++) {
            Integer value = xs[i];
            if (value != null) {
                TreeNode newNode = new TreeNode(value);
                nodes[i] = newNode;
                int parentI = i > 0 ? (i - 1) / 2 : -1;
                if (parentI > -1) {
                    if (i % 2 == 1) {
                        nodes[parentI].left = newNode;
                    }
                    else {
                        nodes[parentI].right = newNode;
                    }
                }
            }
        }
        return nodes[0];
    }

    public static void main(String[] args) {
        Tester.test(
                null,
                (solution, methodName, params) -> new Object[] {
                    build(Arrays.stream(params).toArray(Integer[]::new))
                },
                null,
                r(/*
                ["Solution", "maxLevelSum"]
                [[],[1,7,0,7,-8,null,null]]
                [null,2]
                */)
        );
    }

}
