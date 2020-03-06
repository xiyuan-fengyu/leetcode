package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/6 14:30.
 */
@EnableTemplateString
public class _5_M {

    /*
https://leetcode-cn.com/problems/longest-palindromic-substring/
5. 最长回文子串
给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

示例 1：

输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。
示例 2：

输入: "cbbd"
输出: "bb"
     */

    class Solution {
        public String longestPalindrome(String s) {
            int len = s.length();
            if (len <= 1) {
                return s;
            }

            int newLen = len * 2 + 1;
            char[] newS = new char[newLen];
            for (int i = 0; i < len; i++) {
                newS[i * 2 + 1] = s.charAt(i);
            }

            // 每个位置的最长回文串的右边界
            int[] cache = new int[newLen];
            // 当前正在扩展的中心位置，用做计算对称位置
            int pos = -1;
            // 目前向右扩展到的边界位置
            int maxR = -1;
            // 最长回文的中心位置
            int maxPos = -1;
            // 最长回文长度
            int maxLen = -1;
            int left, right;

            for (int i = 0; i < newLen; ++i) {
                if (i > maxR) {
                    // 位置i已经超过右边界，需要以i为中心向右扩展
                    left = i - 1;
                    right = i + 1;
                    while (left >= 0 && right < newLen) {
                        if (newS[left] == newS[right]) {
                            left--;
                            right++;
                        }
                        else break;
                    }
                    pos = i;
                    maxR = right - 1;
                    cache[i] = right - i;

                    if (maxLen < cache[i] - 1) {
                        maxLen = cache[i] - 1;
                        maxPos = i;
                    }
                }
                else {
                    // 以pos为中心，计算i关于pos的对称位置rI，在以pos为中心，maxR为边界时，i处回文半径不小于rI处的回文半径
                    int rI = cache[pos * 2 - i];
                    if (rI < maxR - i + 1) {
                        cache[i] = rI;
                    }
                    else {
                        right = maxR + 1;
                        left = i * 2 - right;
                        while (left >= 0 && right < newLen) {
                            if (newS[left] == newS[right]) {
                                left--;
                                right++;
                            }
                            else break;
                        }
                        pos = i;
                        maxR = right - 1;
                        cache[i] = right - i;

                        if (maxLen < cache[i] - 1) {
                            maxLen = cache[i] - 1;
                            maxPos = i;
                        }
                    }
                }
            }

            return maxPos % 2 == 1 ? s.substring(maxPos / 2 - cache[maxPos] / 2 + 1, maxPos / 2 - cache[maxPos] / 2 + cache[maxPos])
                    : s.substring((maxPos - 1) / 2 - cache[maxPos] / 2 + 1, (maxPos - 1) / 2 - cache[maxPos] / 2 + cache[maxPos]);
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "longestPalindrome", "longestPalindrome"]
                [[], ["babad"], ["cbbd"]]
                [null, "bab", "bb"]
                */)
        );
    }

}
