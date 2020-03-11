#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    int maxAliveYear(vector<int>& birth, vector<int>& death) {
        vector<int> changes(102, 0);
        for (int i = 0, size = birth.size(); i < size; ++i) {
            changes[birth[i] - 1900]++;
            changes[death[i] - 1900 + 1]--;
        }
        int curAlive = 0;
        int maxAlive = 0;
        int maxAliveYear = 1900;
        for (int i = 0, size = changes.size(); i < size; ++i) {
            if ((curAlive += changes[i]) > maxAlive) {
                maxAlive = curAlive;
                maxAliveYear = 1900 + i;
            }
        }
        return maxAliveYear;
    }
};

int main() {
    Solution solution;
    vector<int> b {1900, 1901, 1950};
    vector<int> d {1948, 1951, 2000};
    cout << solution.maxAliveYear(b, d) << endl;
}
