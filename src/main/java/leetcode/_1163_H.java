package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/19 11:10.
 * https://leetcode-cn.com/contest/weekly-contest-150/problems/last-substring-in-lexicographical-order/
 */
@EnableTemplateString
public class _1163_H {

    /*
    1163. 按字典序排在最后的子串
    给你一个字符串 s，找出它的所有子串并按字典序排列，返回排在最后的那个子串。

    示例 1：
    输入："abab"
    输出："bab"
    解释：我们可以找出 7 个子串 ["a", "ab", "aba", "abab", "b", "ba", "bab"]。按字典序排在最后的子串是 "bab"。

    示例 2：
    输入："leetcode"
    输出："tcode"

    提示：
    1 <= s.length <= 10^5
    s 仅含有小写英文字符。
     */

    class Solution {

        public String lastSubstring(String s) {
            int len = s.length();
            int curMaxIndex = 0;
            int subLen = 1;
            for (int i = 0; i < len; i++) {

            }
            return "";
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "lastSubstring", "lastSubstring"]
                [[], "abab", "leetcode"]
                [null, "bab", "tcode"]
                */)
        );
    }

}
