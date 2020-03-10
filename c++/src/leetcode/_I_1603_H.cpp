#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    static inline vector<int> sub(vector<int>& v1, vector<int>& v2) {
        return {v1[0] - v2[0], v1[1] - v2[1]};
    }

    static inline int crossProduct(vector<int>& v1, vector<int>& v2) {
        return v1[0] * v2[1] - v1[1] * v2[0];
    }

    vector<double> intersection(vector<int>& start1, vector<int>& end1, vector<int>& start2, vector<int>& end2) {
        auto d1 = sub(end1, start1);
        auto d2 = sub(end2, start2);
        auto s1SubS2 = sub(start1, start2);
        auto s1SubS2_CP_d2 = crossProduct(s1SubS2, d2);
        auto s1SubS2_CP_d1 = crossProduct(s1SubS2, d1);
        auto d2_CP_d1 = crossProduct(d2, d1);
        if (d2_CP_d1 != 0) {
            double r1 = s1SubS2_CP_d2 / (double) d2_CP_d1;
            double r2 = s1SubS2_CP_d1 / (double) d2_CP_d1;
            if (0 <= r1 && r1 <= 1 && 0 <= r2 && r2 <= 1) {
                return {start1[0] + r1 * d1[0], start1[1] + r1 * d1[1]};
            }
            return {};
        }
        else {
            if (s1SubS2_CP_d1 == 0) {
                vector<vector<int>> points {
                        {start1[0], start1[1], 1},
                        {end1[0], end1[1], 1},
                        {start2[0], start2[1], 2},
                        {end2[0], end2[1], 2},
                };
                sort(points.begin(), points.end());
                if (points[0][2] != points[1][2] || (points[1][0] == points[2][0] && points[1][1] == points[2][1])) {
                    return {(double) points[1][0], (double) points[1][1]};
                }
            }
            return {};
        }
    }
};

int main() {
    Solution solution;
    vector<int> s1 {0, 0};
    vector<int> e1 {0, 1};
    vector<int> s2 {0, 0};
    vector<int> e2 {0, -1};
    auto res = solution.intersection(s1, e1, s2, e2);
    cout << res[0] << ", " << res[1] << endl;
}
