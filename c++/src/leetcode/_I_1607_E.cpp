#include <iostream>

using namespace std;

class Solution {
public:
    int maximum(int a, int b) {
        long sub = (long) a - b;
        int sign = sub >> 63;
        return (1 + sign) * a + (-sign) * b;
    }
};

int main() {
    Solution solution;
    cout << solution.maximum(2147483647, -2147483648) << endl;
}
