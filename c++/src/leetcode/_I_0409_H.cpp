#include <iostream>
#include <vector>

using namespace std;

class TreeNode {
public:
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int val) : val(val) {}
};

class Solution {
public:
    vector<vector<int>> BSTSequences(TreeNode* root) {
        if (root == nullptr) {
            return {{}};
        }
        vector<TreeNode> queue;
        vector<int> path;
        vector<vector<int>> res;
        this->visit(*root, queue, path, res);
        return res;
    }

private:
    void visit(TreeNode &node, vector<TreeNode> &queue, vector<int> &path, vector<vector<int>> &res) {
        path.push_back(node.val);
        if (node.left != nullptr) {
            queue.push_back(*node.left);
        }
        if (node.right != nullptr) {
            queue.push_back(*node.right);
        }
        if (queue.empty()) {
            vector<int> tempPath;
            tempPath.assign(path.begin(), path.end());
            res.push_back(tempPath);
        }
        else {
            for (int i = 0, size = queue.size(); i < size; ++i) {
                auto cur = queue[i];
                queue.erase(queue.begin() + i);
                visit(cur, queue, path, res);
                queue.insert(queue.begin() + i, cur);
            }
            if (node.right != nullptr) {
                queue.pop_back();
            }
            if (node.left != nullptr) {
                queue.pop_back();
            }
        }
        path.pop_back();
    }
};

int main() {
    auto* root = new TreeNode(5);
    root->left = new TreeNode(2);
    root->left->left = new TreeNode(1);
    root->left->right = new TreeNode(4);
    root->left->left->left = new TreeNode(3);
    auto lists = Solution().BSTSequences(root);
    cout << "" << endl;
}
