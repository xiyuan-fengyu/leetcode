package leetcode;

import com.xiyuan.templateString.EnableTemplateString;
import netscape.security.UserTarget;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/9 14:07.
 */
@EnableTemplateString
public class _I_0409_H {

    /*
https://leetcode-cn.com/problems/bst-sequences-lcci/solution/
面试题 04.09. 二叉搜索树序列
从左向右遍历一个数组，通过不断将其中的元素插入树中可以逐步地生成一棵二叉搜索树。给定一个由不同节点组成的二叉树，输出所有可能生成此树的数组。

示例:
给定如下二叉树

        2
       / \
      1   3
返回:

[
   [2,1,3],
   [2,3,1]
]
     */

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static class Solution {

        public List<List<Integer>> BSTSequences(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root != null) {
                visit(root, new ArrayList<>(), new Stack<>(), res);
            }
            else {
                res.add(new ArrayList<>());
            }
            return res;
        }

        private void visit(TreeNode curNode, ArrayList<TreeNode> queue, Stack<Integer> path, List<List<Integer>> res) {
            path.push(curNode.val);
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
            if (queue.isEmpty()) {
                res.add(new ArrayList<>(path));
            }
            else {
                for (int i = 0, size = queue.size(); i < size; i++) {
                    TreeNode cur = queue.remove(i);
                    visit(cur, queue, path, res);
                    queue.add(i, cur);
                }
                if (curNode.right != null) {
                    queue.remove(queue.size() - 1);
                }
                if (curNode.left != null) {
                    queue.remove(queue.size() - 1);
                }
            }
            path.pop();
        }

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(3);
        List<List<Integer>> lists = new Solution().BSTSequences(root);
        System.out.println(lists);

        Tester.test(
                r(/*
                ["Solution"]
                [[]]
                [null]
                */)
        );
    }

}
