//
// Created by xiyuan_fengyu on 2019/11/21.
//

#include <iostream>
#include <vector>

using namespace std;

int absentMini(const vector<int> &arr) {
    int size = arr.size();
    vector<int> existed(size);
    for (int i = 0; i < size; ++i) {
        int item = arr[i];
        if (item <= size) {
            existed[item - 1] = 1;
        }
    }
    for (int i = 0; i < size; ++i) {
        if (existed[i] == 0) {
            return i + 1;
        }
    }
    return size + 1;
}

int main() {
    vector<int> arr {1, 2, 4};
    cout << absentMini(arr) << endl;
    return 0;
}
