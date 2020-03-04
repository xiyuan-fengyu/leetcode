#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        unordered_map<int, int> existed;
        for (int i = 0, len = nums.size(); i < len; i++) {
            auto num = nums[i];
            auto it = existed.find(target - num);
            if (it != existed.end()) {
                return {it->second, i};
            }
            existed[num] = i;
        }
        return vector<int>{-1, -1};
    }
};