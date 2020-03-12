#include <iostream>
#include <vector>
#include <unordered_map>
#include <cfloat>

using namespace std;

class Solution {
public:
    vector<int> bestLine(vector<vector<int>>& points) {
        vector<int> best {0, 1};
        int maxNum = 2;
        unordered_map<double, vector<int>> lines;
        for (int i = 0, size = points.size(); i < size - maxNum; ++i) {
            lines.clear();
            auto& firstP = points[i];
            int sameToFirst = 1;
            int localMaxNum = 0;
            double localMaxK = 0;
            for (int j = i + 1; j < size; ++j) {
                auto& secondP = points[j];
                int xd = secondP[0] - firstP[0];
                int yd = secondP[1] - firstP[1];
                double k;
                if (xd == 0) {
                    if (yd == 0) {
                        sameToFirst++;
                        continue;
                    }
                    k = DBL_MAX;
                }
                else if (yd == 0) {
                    k = 0;
                }
                else {
                    k = yd / (double) xd;
                }
                auto it = lines.find(k);
                int count;
                if (it == lines.end()) {
                    lines[k] = {i, j, count = 1};
                }
                else {
                    count = ++it->second[2];
                }
                if (localMaxNum < count) {
                    localMaxNum = count;
                    localMaxK = k;
                }
            }
            if (maxNum < sameToFirst + localMaxNum) {
                maxNum = sameToFirst + localMaxNum;
                if (localMaxNum == 0) {
                    best[0] = i;
                    best[1] = i + 1;
                }
                else {
                    vector<int>& line = lines[localMaxK];
                    best[0] = line[0];
                    best[1] = line[1];
                }
            }
        }
        return best;
    }
};

int main() {
    Solution solution;
    vector<vector<int>> points {{0, 0}, {1, 1}, {2, 2}};
    auto res = solution.bestLine(points);
    cout << "[" << res[0] << ", " << res[1] << "]" << endl;
}
