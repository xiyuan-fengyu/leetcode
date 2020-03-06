from typing import List, Dict


class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        existed: Dict[int, int] = {}
        for index, num in enumerate(nums):
            existedIndex = existed.get(target - num)
            if existedIndex is not None:
                return [existedIndex, index]
            existed[num] = index
        return [-1, -1]
