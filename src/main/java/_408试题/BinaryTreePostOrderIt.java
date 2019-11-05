package _408试题;

import java.util.Stack;

/**
 * Created by xiyuan_fengyu on 2019/11/5 16:27.
 */
public class BinaryTreePostOrderIt {

    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }

    static class VisitItem {
        TreeNode node;
        boolean leftVisited = false;
        boolean rightVisited = false;
        public VisitItem(TreeNode node) {
            this.node = node;
        }
    }

    /**
     * 非递归后续遍历
     */
    static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<VisitItem> stack = new Stack<>();
        stack.push(new VisitItem(root));
        while (!stack.isEmpty()) {
            VisitItem item = stack.peek();
            if (!item.leftVisited) {
                item.leftVisited = true;
                if (item.node.left != null) {
                    stack.push(new VisitItem(item.node.left));
                }
            }
            else if (!item.rightVisited) {
                item.rightVisited = true;
                if (item.node.right != null) {
                    stack.push(new VisitItem(item.node.right));
                }
            }
            else {
                System.out.println(stack.pop().node.value);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        postOrder(root);
    }

}