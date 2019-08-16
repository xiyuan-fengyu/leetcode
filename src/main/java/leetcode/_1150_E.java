package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 9:39.
 */
@EnableTemplateString
public class _1150_E {

    class Solution {
        public boolean isMajorityElement(int[] nums, int target) {
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    count++;
                }
            }
            return count > nums.length / 2;
        }
    }

    public static void main(String[] args) {
        Tester.test(r(/*
        ["Solution", "isMajorityElement", "isMajorityElement"]
        [[], [[2,4,5,5,5,5,5,6,6], 5], [[10,100,101,101], 101]]
        [null, true, false]
        */));
    }

}
