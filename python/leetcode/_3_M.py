from typing import Dict


class Solution(object):
    def lengthOfLongestSubstring(self, s: str):
        res = 0
        existedIndexes: Dict[str, int] = {}
        left = -1
        right = 0
        sLen = len(s)
        while right < sLen:
            ei = existedIndexes.get(s[right])
            if ei is None:
                ei = -1
            left = max(left, ei)
            existedIndexes[s[right]] = right
            res = max(res, right - left)
            right += 1
        return res

if __name__ == '__main__':
    print(Solution().lengthOfLongestSubstring("test"))
