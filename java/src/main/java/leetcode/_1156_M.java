package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/14 9:42.
 https://leetcode-cn.com/problems/swap-for-maximum-repeated-substring/
 两个指针
 */
@EnableTemplateString
public class _1156_M {

    /*
    1156. 单字符重复子串的最大长度
    如果字符串中的所有字符都相同，那么这个字符串是单字符重复的字符串。
    给你一个字符串 text，你只能交换其中两个字符一次或者什么都不做，然后得到一些单字符重复的子串。返回其中最长的子串的长度。

    示例 1：
    输入：text = "ababa"
    输出：3

    示例 2：
    输入：text = "aaabaaa"
    输出：6

    示例 3：
    输入：text = "aaabbaaa"
    输出：4

    示例 4：
    输入：text = "aaaaa"
    输出：5

    示例 5：
    输入：text = "abcdef"
    输出：1

    提示：
    1 <= text.length <= 20000
    text 仅由小写英文字母组成。
     */

    class Solution {

        public int maxRepOpt1(String text) {
            int len = text.length();
            if (len == 1) {
                return 1;
            }
            else if (len == 2) {
                return text.charAt(0) == text.charAt(1) ? 2 : 1;
            }

            char[] chars = text.toCharArray();
            int[] charCounts = new int['z' - 'a' + 1];
            for (int i = 0; i < len; i++) {
                charCounts[chars[i] - 'a']++;
            }

            int maxLen = 0;
            int batchL = 0;
            int batchR = 0;
            while (batchR < len) {
                boolean diffExisted = false;
                char c = chars[batchL];
                int count = 1;
                batchR = batchL + 1;
                for (; batchR < len; batchR++) {
                    if (c != chars[batchR]) {
                        if (diffExisted) {
                            break;
                        }
                        else {
                            diffExisted = true;
                            batchL = batchR;
                        }
                    }
                    else {
                        count++;
                    }
                }

                count = Math.min(count + 1, charCounts[c - 'a']);
                if (count > maxLen) {
                    maxLen = count;
                }
            }
            return maxLen;
        }

    }

    public static void main(String[] args) {
        Tester.test(r(/*
        ["Solution","maxRepOpt1","maxRepOpt1","maxRepOpt1","maxRepOpt1","maxRepOpt1"]
        [[],"ababa","aaabaaa","aaabbaaa","aaaaa","abcdef"]
        [null,3,6,4,5,1]
        */));
    }

}
