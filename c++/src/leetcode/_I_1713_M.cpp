#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;


//class DictNode {
//public:
//    char val;
//    bool end;
//    vector<DictNode*> children;
//
//    DictNode(char val, bool end) : val(val), end(end), children(vector<DictNode*>(26, nullptr)) {
//    }
//
//    DictNode* addChild(char val, bool end) {
//        DictNode* child = children[val - 97];
//        if (child == nullptr) {
//            children[val - 97] = child = new DictNode(val, end);
//        }
//        else {
//            child->end = child->end || end;
//        }
//        return child;
//    }
//
//    DictNode* child(char val) {
//        return children[val - 97];
//    }
//
//    ~DictNode() {
//        for (DictNode* child : children) {
//            delete(child);
//        }
//    }
//
//};
//
//class DictTree {
//public:
//    DictTree(vector<string>& words) : root(DictNode(0, false)) {
//        for (string& word : words) {
//            addWord(word);
//        }
//    }
//
//    int check(string& str, int from, vector<int>& dp) {
//        if (dp[from] > -1) {
//            return dp[from];
//        }
//        int sLen = str.length();
//        int minUnknown = sLen - from;
//        DictNode* cur = &root;
//        for (int i = from; cur != nullptr && i < sLen; ++i) {
//            cur = cur->child(str[i]);
//            if (cur != nullptr && cur->end) {
//                minUnknown = min(minUnknown, check(str, i + 1, dp));
//            }
//            else {
//                minUnknown = min(minUnknown, i - from + 1 + check(str, i + 1, dp));
//            }
//        }
//        return dp[from] = minUnknown;
//    }
//
//private:
//    DictNode root;
//
//    void addWord(string& word) {
//        DictNode* cur = &root;
//        for (int i = 0, len = word.length(); i < len; i++) {
//            cur = cur->addChild(word[i], i + 1 == len);
//        }
//    }
//};
//
//class Solution {
//public:
//    int respace(vector<string>& dictionary, string sentence) {
//        int sLen = sentence.length();
//        if (sLen == 0) {
//            return 0;
//        }
//
//        DictTree dictTree(dictionary);
//        vector<int> dp(sLen + 1, -1);
//        dp[sLen] = 0;
//        return dictTree.check(sentence, 0, dp);
//    }
//};

class Solution_v1 {
public:
    int respace(vector<string>& dictionary, string sentence) {
        int sLen = sentence.length();
        if (sLen == 0) {
            return 0;
        }

        unordered_map<char, vector<string>> dic;
        for (string& str : dictionary) {
            auto it = dic.find(str[0]);
            vector<string>& words = it != dic.end() ? it->second : dic[str[0]] = vector<string>();
            words.push_back(str);
        }
        vector<int> dp(sLen + 1);
        for (int i = 0; i <= sLen; ++i) {
            dp[i] = i;
        }
        for (int i = 0; i < sLen; ++i) {
            auto it = dic.find(sentence[i]);
            if (it != dic.end()) {
                vector<string>& words = it->second;
                for (string& word : words) {
                    int wLen = word.length();
                    if (i + wLen <= sLen && word == sentence.substr(i, wLen)) {
                        dp[i + wLen] = min(dp[i + wLen], dp[i]);
                    }
                }
            }
            dp[i + 1] = min(dp[i + 1], dp[i] + 1);
        }
        return dp[sLen];
    }
};

class Solution {
public:
    int check(vector<vector<int>>& wordFound, int from, vector<int>& dp) {
        if (dp[from] > -1) {
            return dp[from];
        }
        int sLen = wordFound.size();
        int minUnknown = 1 + check(wordFound, from + 1, dp);
        auto& wordLens = wordFound[from];
        for (int wLen : wordLens) {
            minUnknown = min(minUnknown, check(wordFound, from + wLen, dp));
        }
        return dp[from] = minUnknown;
    }

    int respace(vector<string>& dictionary, string& sentence) {
        int sLen = sentence.length();
        if (sLen == 0) {
            return 0;
        }

        vector<vector<int>> wordFound(sLen, vector<int>());
        for (string& str : dictionary) {
            int pos = -1;
            int strLen = str.length();
            while ((pos = sentence.find(str, pos + 1)) > -1) {
                wordFound[pos].push_back(strLen);
            }
        }

        vector<int> dp(sLen + 1, -1);
        dp[sLen] = 0;
        return check(wordFound, 0, dp);
    }
};

int main() {
    Solution solution;
    vector<string> dic {"looked","just","like","her","brother"};
    string str("jesslookedjustliketimherbrother");
    cout << solution.respace(dic, str) << endl;
}
