
class TreeNode {
    val: number;
    left: TreeNode;
    right: TreeNode;
    constructor(val: number) {
        this.val = val;
    }
}

function visit(node: TreeNode, queue: TreeNode[], path: number[], res: number[][]) {
    path.push(node.val);
    if (node.left != null) {
        queue.push(node.left);
    }
    if (node.right != null) {
        queue.push(node.right);
    }
    if (queue.length == 0) {
        res.push(Array.from(path));
    }
    else {
        for (let i = 0, len = queue.length; i < len; i++) {
            let cur = queue.splice(i, 1)[0];
            visit(cur, queue, path, res);
            queue.splice(i, 0, cur);
        }
        if (node.right != null) {
            queue.pop();
        }
        if (node.left != null) {
            queue.pop();
        }
    }
    path.pop();
}

function BSTSequences(root: TreeNode) {
    if (root == null) {
        return [[]];
    }
    const res = [];
    visit(root, [], [], res);
    return res;
}

const root = new TreeNode(5);
root.left = new TreeNode(2);
root.left.left = new TreeNode(1);
root.left.right = new TreeNode(4);
root.left.left.left = new TreeNode(3);
const lists = BSTSequences(root);
console.log(lists);
