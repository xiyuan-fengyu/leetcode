from typing import List


class TreeNode:
    def __init__(self, val):
        self.val = val
        self.left = None
        self.right = None


class Solution:

    def visit(self, node: TreeNode, queue: List[TreeNode], path: List[int], res: List[List[int]]):
        path.append(node.val)
        if node.left:
            queue.append(node.left)
        if node.right:
            queue.append(node.right)
        if len(queue) == 0:
            res.append(path.copy())
        else:
            for i, cur in enumerate(queue):
                self.visit(cur, queue[:i] + queue[i + 1:], path, res)
            if node.right:
                queue.pop()
            if node.left:
                queue.pop()
        path.pop()

    def BSTSequences(self, root: TreeNode) -> List[List[int]]:
        if root is None:
            return [[]]
        res = []
        self.visit(root, [], [], res)
        return res


if __name__ == '__main__':
    root = TreeNode(5)
    root.left = TreeNode(2)
    root.left.left = TreeNode(1)
    root.left.right = TreeNode(4)
    root.left.left.left = TreeNode(3)
    lists = Solution().BSTSequences(root)
    print(lists)
