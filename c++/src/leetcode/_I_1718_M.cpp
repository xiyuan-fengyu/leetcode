#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

template <typename Key, typename Value>
const Value& getOrDefault(std::unordered_map<Key, Value>& m, const Key& key, const Value& default_value)
{
    auto it = m.find(key);
    if (it == m.end()) {
        return default_value;
    }
    return it->second;
}

class Solution {
public:
    vector<int> shortestSeq(vector<int>& big, vector<int>& small) {
        int bLen = big.size();
        int sLen = small.size();
        if (bLen < sLen) {
            return {};
        }

        vector<int> minSeq {};
        int minSeqLen = bLen + 1;
        int left = 0;
        int right = 0;
        int contains = 0;
        unordered_map<int, int> numCounts;
        for (int num : small) {
            numCounts[num] = 0;
        }
        while (right < bLen) {
            while (right < bLen && contains < sLen) {
                int count = getOrDefault(numCounts, big[right], -1);
                if (count > -1) {
                    if (count == 0) {
                        contains++;
                    }
                    numCounts[big[right]] = count + 1;
                }
                right++;
            }
            if (contains == sLen) {
                while (left < right) {
                    int count = getOrDefault(numCounts, big[left], -1);
                    if (count > -1) {
                        if (contains + 1 == sLen && count == 1) {
                            break;
                        }
                        else if (count == 1) {
                            if (minSeqLen > right - left) {
                                minSeqLen = right - left;
                                minSeq = {left, right - 1};
                            }
                            contains--;
                        }
                        numCounts[big[left]] = count - 1;
                    }
                    left++;
                }
            }
        }
        return minSeq;
    }
};

int main() {
    Solution solution;
    vector<int> big {7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7};
    vector<int> small {1,5,9};
    auto res = solution.shortestSeq(big, small);
    cout << "[" << res[0] << ", " << res[1] << "]" << endl;
}
