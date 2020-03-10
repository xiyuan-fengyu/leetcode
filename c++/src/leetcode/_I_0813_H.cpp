#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    int pileBox(vector<vector<int>>& boxes) {
        int maxH = 0;
        int len = boxes.size();
        if (len > 0) {
            // 默认的比较器和下面等效，但默认的效率更高
//            , [](auto box1, auto box2) {
//                return box1[0] != box2[0] ? box1[0] < box2[0] :
//                       box1[1] != box2[1] ? box1[1] < box2[1] : box1[2] < box2[2];
//            }
            sort(boxes.begin(), boxes.end());
            vector<int> cache(len, 0);
            cache[0] = maxH = boxes[0][2];
            for (int i = 1; i < len; ++i) {
                auto& box = boxes[i];
                int localMaxH = 0;
                for (int j = 0; j < i; ++j) {
                    auto& prevBox = boxes[j];
                    if (prevBox[0] < box[0] && prevBox[1] < box[1] && prevBox[2] < box[2]) {
                        if (localMaxH < cache[j]) {
                            localMaxH = cache[j];
                        }
                    }
                }
                if (maxH < (cache[i] = localMaxH + box[2])) {
                    maxH = cache[i];
                }
            }
        }
        return maxH;
    }
};

int main() {
    Solution solution;
    vector<vector<int>> boxes {{1, 1, 1}, {3, 4, 5}, {2, 3, 4}, {2, 6, 7}};
    cout << solution.pileBox(boxes) << endl;
}
