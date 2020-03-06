package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/5 15:27.
 */
@EnableTemplateString
public class _3_M {

    /*
    https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
    3. 无重复字符的最长子串
    给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

    示例 1:

    输入: "abcabcbb"
    输出: 3
    解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
    示例 2:

    输入: "bbbbb"
    输出: 1
    解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
    示例 3:

    输入: "pwwkew"
    输出: 3
    解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
         请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    class Solution {
        public int lengthOfLongestSubstring(String s) {
            int res = 0;
            int[] existedIndexes = new int[128];
            Arrays.fill(existedIndexes, -1);
            for (int left = -1, right = 0, sLen = s.length(); right < sLen; right++) {
                left = Math.max(left, existedIndexes[s.charAt(right)]);
                existedIndexes[s.charAt(right)] = right;
                res = Math.max(res, right - left);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution"]
                [[]]
                [null]
                */)
        );
    }

}
