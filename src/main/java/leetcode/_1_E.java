package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.HashMap;
import java.util.Map;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/3 18:18.
 * https://leetcode-cn.com/problems/two-sum/
 */
@EnableTemplateString
public class _1_E {

    /*
    1. 两数之和
    给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

    你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

    示例:

    给定 nums = [2, 7, 11, 15], target = 9

    因为 nums[0] + nums[1] = 2 + 7 = 9
    所以返回 [0, 1]
     */
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> existed = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                Integer otherIndex = existed.get(target - num);
                if (otherIndex != null) {
                    return new int[] {otherIndex, i};
                }
                else {
                    existed.put(num, i);
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "twoSum"]
                [[], [[2, 7, 11, 15], 9]]
                [null, [0, 1]]
                */)
        );
    }

}
