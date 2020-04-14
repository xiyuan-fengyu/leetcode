from typing import List


class Solution:
    def missingTwo(self, nums: List[int]) -> List[int]:
        n = len(nums) + 2
        sum = (1 + n) * n // 2
        sumWithoutAB = 0
        for num in nums:
            sumWithoutAB += num
        sumAB = sum - sumWithoutAB
        halfAB = sumAB // 2
        sum1 = 0
        for num in nums:
            if num <= halfAB:
                sum1 += num
        a = (1 + halfAB) * halfAB // 2 - sum1
        return [a, sumAB - a]


if __name__ == '__main__':
    print(Solution().missingTwo([1]))
