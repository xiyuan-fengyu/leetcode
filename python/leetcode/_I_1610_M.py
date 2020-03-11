from typing import List


class Solution:
    def maxAliveYear(self, birth: List[int], death: List[int]) -> int:
        num = len(birth)
        changes = [0] * 102
        for i in range(num):
            changes[birth[i] - 1900] += 1
            changes[death[i] - 1900 + 1] -= 1
        curAlive = 0
        maxAlive = 0
        maxAliveYear = 1900
        for i, change in enumerate(changes):
            curAlive += change
            if maxAlive < curAlive:
                maxAlive = curAlive
                maxAliveYear = 1900 + i
        return maxAliveYear


if __name__ == '__main__':
    print(Solution().maxAliveYear([1900, 1901, 1950], [1948, 1951, 2000]))
