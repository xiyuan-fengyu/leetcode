from typing import List, Dict


class Solution:
    def shortestSeq(self, big: List[int], small: List[int]) -> List[int]:
        bLen = len(big)
        sLen = len(small)
        if bLen < sLen:
            return []
        minSeqLen = bLen + 1
        minSeq = []
        left = 0
        right = 0
        contains = 0
        numCounts: Dict[int, int] = {}
        for num in small:
            numCounts[num] = 0
        while right < bLen:
            while right < bLen and contains < sLen:
                count = numCounts.get(big[right], -1)
                if count > -1:
                    if count == 0:
                        contains += 1
                    numCounts[big[right]] = count + 1
                right += 1
            if contains == sLen:
                while left < right:
                    count = numCounts.get(big[left], -1)
                    if count > -1:
                        if contains + 1 == sLen and count == 1:
                            break
                        elif count == 1:
                            if minSeqLen > right - left:
                                minSeqLen = right - left
                                minSeq = [left, right - 1]
                            contains -= 1
                        numCounts[big[left]] = count - 1
                    left += 1
        return minSeq


if __name__ == '__main__':
    print(Solution().shortestSeq([7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7], [1,5,9]))
