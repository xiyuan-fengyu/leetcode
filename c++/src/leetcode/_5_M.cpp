#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    string longestPalindrome(const string& s) {
        int sLen = s.length();
        if (sLen <= 1) {
            return s;
        }

        int newLen = sLen * 2 + 1;
        vector<int> posRadius(newLen, 0);
        vector<char> newS(newLen, '\0');
        for (int i = 0; i < sLen; i++) {
            newS[i * 2 + 1] = s[i];
        }

        int pos = -1;
        int maxR = -1;
        int maxLen = -1;
        int maxPos = -1;
        int left, right;

        for (int i = 0; i < newLen; i++) {
            if (maxR < i) {
                left = i - 1;
                right = i + 1;
                while (left > -1 && right < newLen) {
                    if (newS[left] == newS[right]) {
                        left--;
                        right++;
                    }
                    else break;
                }
                pos = i;
                maxR = right - 1;
                posRadius[i] = right - i;
                if (maxLen < posRadius[i] - 1) {
                    maxLen = posRadius[i] - 1;
                    maxPos = i;
                }
            }
            else {
                int rI = posRadius[pos * 2 - i];
                if (rI < maxR - i + 1) {
                    posRadius[i] = rI;
                }
                else {
                    right = maxR + 1;
                    left = i * 2 - right;
                    while (left > -1 && right < newLen) {
                        if (newS[left] == newS[right]) {
                            left--;
                            right++;
                        }
                        else break;
                    }
                    pos = i;
                    maxR = right - 1;
                    posRadius[i] = right - i;
                    if (maxLen < posRadius[i] - 1) {
                        maxLen = posRadius[i] - 1;
                        maxPos = i;
                    }
                }
            }
        }
        return maxPos % 2 ? s.substr(maxPos / 2 - posRadius[maxPos] / 2 + 1, posRadius[maxPos] - 1)
            : s.substr((maxPos - 1) / 2 - posRadius[maxPos] / 2 + 1, posRadius[maxPos] - 1);
    }
};

int main() {
    Solution solution;
    cout << solution.longestPalindrome("babad") << endl;
    cout << solution.longestPalindrome("lcnvoknqgejxbfhijmxglisfzjwbtvhodwummdqeggzfczmetrdnoetmcydwddmtubcqmdjwnpzdqcdhplxtezctvgnpobnnscrmeqkwgiedhzsvskrxwfyklynkplbgefjbyhlgmkkfpwngdkvwmbdskvagkcfsidrdgwgmnqjtdbtltzwxaokrvbxqqqhljszmefsyewwggylpugmdmemvcnlugipqdjnriythsanfdxpvbatsnatmlusspqizgknabhnqayeuzflkuysqyhfxojhfponsndytvjpbzlbfzjhmwoxcbwvhnvnzwmkhjxvuszgtqhctbqsxnasnhrusodeqmzrlcsrafghbqjpyklaaqximcjmpsxpzbyxqvpexytrhwhmrkuybtvqhwxdqhsnbecpfiudaqpzsvfaywvkhargputojdxonvlprzwvrjlmvqmrlftzbytqdusgeupuofhgonqoyffhmartpcbgybshllnjaapaixdbbljvjomdrrgfeqhwffcknmcqbhvulwiwmsxntropqzefwboozphjectnudtvzzlcmeruszqxvjgikcpfclnrayokxsqxpicfkvaerljmxchwcmxhtbwitsexfqowsflgzzeynuzhtzdaixhjtnielbablmckqzcccalpuyahwowqpcskjencokprybrpmpdnswslpunohafvminfolekdleusuaeiatdqsoatputmymqvxjqpikumgmxaxidlrlfmrhpkzmnxjtvdnopcgsiedvtfkltvplfcfflmwyqffktsmpezbxlnjegdlrcubwqvhxdammpkwkycrqtegepyxtohspeasrdtinjhbesilsvffnzznltsspjwuogdyzvanalohmzrywdwqqcukjceothydlgtocukc") << endl;
}
