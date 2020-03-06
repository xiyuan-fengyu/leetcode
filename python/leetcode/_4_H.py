import sys
from typing import List


class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        len1 = len(nums1)
        len2 = len(nums2)
        k = (len1 + len2 + 1) // 2
        offset1 = 0
        offset2 = 0
        while k != 1:
            kHalf = k // 2
            v1 = nums1[offset1 + kHalf - 1] if offset1 + kHalf - 1 < len1 else sys.maxsize
            v2 = nums2[offset2 + kHalf - 1] if offset2 + kHalf - 1 < len2 else sys.maxsize
            if v1 < v2:
                offset1 += kHalf
            else:
                offset2 += kHalf
            k -= kHalf
        v1 = nums1[offset1] if offset1 < len1 else sys.maxsize
        v2 = nums2[offset2] if offset2 < len2 else sys.maxsize
        if (len1 + len2) % 2 == 0:
            if v1 == v2:
                return v1
            elif v1 < v2:
                v1R = nums1[offset1 + 1] if offset1 + 1 < len1 else sys.maxsize
                return (v1 + min(v1R, v2)) / 2
            else:
                v2R = nums2[offset2 + 1] if offset2 + 1 < len2 else sys.maxsize
                return (v2 + min(v2R, v1)) / 2
        else:
            return min(v1, v2)


if __name__ == '__main__':
    print(Solution().findMedianSortedArrays(
        [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22],
        [0,6]
    ))
