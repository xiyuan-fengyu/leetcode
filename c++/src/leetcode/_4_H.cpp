#include <iostream>
#include <vector>
#include <climits>

using namespace std;

class Solution {
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        int len1 = nums1.size();
        int len2 = nums2.size();
        int k = (len1 + len2 + 1) / 2;
        int offset1 = 0;
        int offset2 = 0;
        while (k != 1) {
            int kHalf = k / 2;
            int v1 = offset1 + kHalf - 1 < len1 ? nums1[offset1 + kHalf - 1] : INT_MAX;
            int v2 = offset2 + kHalf - 1 < len2 ? nums2[offset2 + kHalf - 1] : INT_MAX;
            if (v1 < v2) {
                offset1 += kHalf;
            }
            else {
                offset2 += kHalf;
            }
            k -= kHalf;
        }

        int v1 = offset1 < len1 ? nums1[offset1] : INT_MAX;
        int v2 = offset2 < len2 ? nums2[offset2] : INT_MAX;
        if ((len1 + len2) % 2 == 0) {
            if (v1 == v2) {
                return v1;
            }
            else if (v1 < v2) {
                int v1R = offset1 + 1 < len1 ? nums1[offset1 + 1] : INT_MAX;
                return (v1 + min(v1R, v2)) / 2.0;
            }
            else {
                int v2R = offset2 + 1 < len2 ? nums2[offset2 + 1] : INT_MAX;
                return (v2 + min(v2R, v1)) / 2.0;
            }
        }
        else {
            return min(v1, v2);
        }
    }
};

int main() {
    Solution solution;
    vector<int> vector1 {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
    vector<int> vector2 {0,6};
    cout << solution.findMedianSortedArrays(vector1, vector2) << " == 10.5" << endl;
}
