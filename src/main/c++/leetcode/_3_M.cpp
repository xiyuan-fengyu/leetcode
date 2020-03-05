#include <string>
#include <vector>
#include <iostream>

using namespace std;

class Solution {
public:
    int lengthOfLongestSubstring(const string& s) {
        int res = 0;
        int sLen = s.length();
        int left = -1;
        int right = 0;
        vector<int> existedIndexes(128, -1);
        for (; right < sLen; ++right) {
            left = max(left, existedIndexes[s[right]]);
            existedIndexes[s[right]] = right;
            res = max(res, right - left);
        }
        return res;
    }
};

int main() {
    Solution solution;
    cout << solution.lengthOfLongestSubstring("abcabcbb") << endl;
    cout << solution.lengthOfLongestSubstring("bbbbb") << endl;
    cout << solution.lengthOfLongestSubstring("pwwkew") << endl;
}
