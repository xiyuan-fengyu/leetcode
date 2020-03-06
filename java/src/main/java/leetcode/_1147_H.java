package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import static com.xiyuan.templateString.TemplateString.S.r;

/*
https://leetcode-cn.com/problems/longest-chunked-palindrome-decomposition/
1147. 段式回文
段式回文 其实与 一般回文 类似，只不过是最小的单位是 一段字符 而不是 单个字母。
举个例子，对于一般回文 "abcba" 是回文，而 "volvo" 不是，但如果我们把 "volvo" 分为 "vo"、"l"、"vo" 三段，则可以认为 “(vo)(l)(vo)” 是段式回文（分为 3 段）。
给你一个字符串 text，在确保它满足段式回文的前提下，请你返回 段 的 最大数量 k。
如果段的最大数量为 k，那么存在满足以下条件的 a_1, a_2, ..., a_k：
每个 a_i 都是一个非空字符串；
将这些字符串首位相连的结果 a_1 + a_2 + ... + a_k 和原始字符串 text 相同；
对于所有1 <= i <= k，都有 a_i = a_{k+1 - i}。

示例 1：
输入：text = "ghiabcdefhelloadamhelloabcdefghi"
输出：7
解释：我们可以把字符串拆分成 "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"。

示例 2：
输入：text = "merchant"
输出：1
解释：我们可以把字符串拆分成 "(merchant)"。

示例 3：
输入：text = "antaprezatepzapreanta"
输出：11
解释：我们可以把字符串拆分成 "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)"。

示例 4：
输入：text = "aaa"
输出：3
解释：我们可以把字符串拆分成 "(a)(a)(a)"。

提示：
text 仅由小写英文字符组成。
1 <= text.length <= 1000

关键字:
指针
回文串
入栈出栈
 */

/**
 * Created by xiyuan_fengyu on 2019/8/21 17:11.
 */
@EnableTemplateString
public class _1147_H {

    class Solution_1 {

        public int longestDecomposition(String text) {
            int len = text.length();
            if (len <= 1) {
                return 1;
            }

            char[] chars = text.toCharArray();
            char[] stack = new char[len];
            int count = 0;
            int stackI = -1;
            for (int left = 0, right; left < len; left++) {
                right = len - 1 - left;
                // left字母入栈，stackI右移
                char cL = chars[left];
                if (stackI == -1 || cL != stack[stackI]) {
                    // 左字母入栈
                    stack[++stackI] = cL;
                }
                else if (--stackI == -1) {
                    // 出栈后，栈空，完成一次段式回文匹配
                    count++;
                }

                char cR = chars[right];
                if (stackI == -1 || cR != stack[stackI]) {
                    // 右字母入栈
                    stack[++stackI] = cR;
                }
                else if (--stackI == -1) {
                    // 出栈后，栈空，完成一次段式回文匹配
                    count++;
                }
            }
            return count;
        }

    }

    class Solution {

        public int longestDecomposition(String text) {
            int len = text.length();
            if (len <= 1) {
                return 1;
            }

            char[] chars = text.toCharArray();
            char[] stack = new char[len];
            int count = 0;
            int stackI = -1;
            // 只用分析一半即可
            for (int left = 0, right = len - 1, half = (len - 1) / 2; left <= half; left++, right--) {
                // left字母入栈，stackI右移
                char leftChar = chars[left];
                if (stackI == -1 || leftChar != stack[stackI]) {
                    // 左字母入栈
                    stack[++stackI] = leftChar;
                }
                else {
                    // 出栈
                    --stackI;
                }

                char rightChar = chars[right];
                if (stackI == -1 || rightChar != stack[stackI]) {
                    // 右字母入栈
                    stack[++stackI] = rightChar;
                }
                else if (--stackI == -1) {
                    // 出栈后，栈空，完成一次段式回文匹配
                    count += left == right ? 1 : 2;
                }
            }
            if (stackI > -1) {
                // 栈内有剩余元素，count需要加一
                count++;
            }
            return count;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "longestDecomposition", "longestDecomposition", "longestDecomposition", "longestDecomposition"]
                [[], "ghiabcdefhelloadamhelloabcdefghi", "merchant", "antaprezatepzapreanta", "aaa"]
                [null, 7, 1, 11, 3]
                */)
        );
    }

}
