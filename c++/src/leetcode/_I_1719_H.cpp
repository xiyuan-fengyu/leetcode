#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    vector<int> missingTwo(vector<int>& nums) {
        int n = nums.size() + 2;
        int sum = (1 + n) * n / 2;
        int sumWithoutAB = 0;
        for (int num : nums) {
            sumWithoutAB += num;
        }
        int sumAB = sum - sumWithoutAB;
        int halfAB = sumAB / 2;
        int sum1 = 0;
        for (int num : nums) {
            if (num <= halfAB) {
                sum1 += num;
            }
        }
        int a = (1 + halfAB) * halfAB / 2 - sum1;
        return {a, sumAB - a};
    }

};

int main() {
    Solution solution;
    vector<int> nums {1};
    auto res = solution.missingTwo(nums);
    cout << res[0] << "," << res[1] << endl;
}
