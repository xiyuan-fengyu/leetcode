package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/6 11:55.
 */
@EnableTemplateString
public class _4_H {

    /*
    https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
    4. 寻找两个有序数组的中位数
    给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。

    请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。

    你可以假设 nums1 和 nums2 不会同时为空。

    示例 1:

    nums1 = [1, 3]
    nums2 = [2]

    则中位数是 2.0
    示例 2:

    nums1 = [1, 2]
    nums2 = [3, 4]

    则中位数是 (2 + 3)/2 = 2.5
     */

    class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int len1 = nums1.length;
            int len2 = nums2.length;
            int k = (len1 + len2 + 1) / 2;
            int offset1 = 0;
            int offset2 = 0;
            while (k != 1) {
                int kHalf = k / 2;
                int v1 = offset1 + kHalf - 1 < len1 ? nums1[offset1 + kHalf - 1] : Integer.MAX_VALUE;
                int v2 = offset2 + kHalf - 1 < len2 ? nums2[offset2 + kHalf - 1] : Integer.MAX_VALUE;
                if (v1 < v2) {
                    offset1 += kHalf;
                }
                else {
                    offset2 += kHalf;
                }
                k -= kHalf;
            }
            int v1 = offset1 < len1 ? nums1[offset1] : Integer.MAX_VALUE;
            int v2 = offset2 < len2 ? nums2[offset2] : Integer.MAX_VALUE;
            if ((len1 + len2) % 2 == 0) {
                if (v1 == v2) {
                    return v1;
                }
                else if (v1 < v2) {
                    int v1R = offset1 + 1 < len1 ? nums1[offset1 + 1] : Integer.MAX_VALUE;
                    return (v1 + Math.min(v1R, v2)) / 2.0;
                }
                else {
                    int v2R = offset2 + 1 < len2 ? nums2[offset2 + 1] : Integer.MAX_VALUE;
                    return (v2 + Math.min(v2R, v1)) / 2.0;
                }
            }
            else {
                return Math.min(v1, v2);
            }
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "findMedianSortedArrays"]
                [[], [[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22],[0,6]]]
                [null, 10.5]
                */)
        );
    }

}
