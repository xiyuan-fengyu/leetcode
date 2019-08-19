package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/16 17:44.
 * https://leetcode-cn.com/contest/biweekly-contest-6/problems/string-transforms-into-another-string/
 */
@EnableTemplateString
public class _1153_H {

    /*
    1153. 字符串转化
    给出两个长度相同的字符串，分别是 str1 和 str2。请你帮忙判断字符串 str1 能不能在 零次 或 多次 转化后变成字符串 str2。
    每一次转化时，将会一次性将 str1 中出现的 所有 相同字母变成其他 任何 小写英文字母（见示例）。
    只有在字符串 str1 能够通过上述方式顺利转化为字符串 str2 时才能返回 True，否则返回 False。​​

    实例1：
    输入：str1 = "aabcc", str2 = "ccdee"
    输出：true
    解释：将 'c' 变成 'e'，然后把 'b' 变成 'd'，接着再把 'a' 变成 'c'。注意，转化的顺序也很重要。

    实例2：
    输入：str1 = "leetcode", str2 = "codeleet"
    输出：false
    解释：我们没有办法能够把 str1 转化为 str2。

    提示：
    1 <= str1.length == str2.length <= 10^4
    str1 和 str2 中都只会出现 小写英文字母
     */

    /**
     * 解题思路
     * 字母的转换一共有以下几种特殊情况
     * 1. 转换前后字符串完全相等，可以转换
     * 2. 下游分叉的一定不能转换
     *  例如：a->b, a->c
     * 3. 有空闲字母的一定可以转换
     *  没有上游的字母就视为空闲字母，因为没有上游的字母可能没在转换链中出现，也可能在无环链末端(a->b->c->d,a空闲) 也可能在环链的上游侧链末端(a->b->c->d->a,f->e->a,f空闲)，后面两种经过转换后，末端的字母就会空闲出来
     *  无环链自身可以从头到尾依次转换；有环链可以借助空闲字母完成转换
     *  单独把1这种情况提出来，是因为 abcdefghijklmnopqrstuvwxyz -> abcdefghijklmnopqrstuvwxyz 这种情况所有字母的本身即为上游，没有空闲字母，所以要单独放在第一种情况来处理
     */
    class Solution {

        public boolean canConvert(String str1, String str2) {
            int len = str1.length();
            if (len <= 1 || str1.equals(str2)) {
                // 两个字符串完全相等时，会影响后面上游字母的判定，所以先在这里判断了
                return true;
            }

            char[] chars1 = str1.toCharArray();
            char[] chars2 = str2.toCharArray();

            // 记录下游情况，下游分叉则不可转换
            char[] lowerReaches = new char['z' + 1];
            // 记录上游情况，用来标记空闲字母，如果一个字母没有上游，则算作空闲字母(初始状态就是空闲的，或者经过一定步骤转换后可以变为空闲状态)
            // 如果有空闲字母，且不存在下游分叉的情况，一定可以转换
            char[] upperReaches = new char['z' + 1];
            int freeCharNum = 26;
            for (int i = 0; i < len; i++) {
                char char1 = chars1[i];
                char char2 = chars2[i];

                if (lowerReaches[char1] != 0 && lowerReaches[char1] != char2) {
                    // 该字母下游分叉，不可转换
                    return false;
                }
                else {
                    // 更新 char2 的上游字母情况
                    char upperReach = upperReaches[char2];
                    if (upperReach == 0) {
                        if (--freeCharNum == 0) {
                            return false;
                        }
                        upperReaches[char2] = char1;
                    }
                    // 更新 char2 的下游字母情况
                    lowerReaches[char1] = char2;
                }
            }
            return true;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "canConvert", "canConvert", "canConvert", "canConvert", "canConvert", "canConvert"]
                [[], ["aabcc", "ccdee"], ["leetcode", "codeleet"], ["abcdefghijklmnopqrstuvwxy", "bcdefghijkamnopqrstuvwxyz"], ["abcdefghijklmnopqrstuvwxyz", "zabcdefghijklmnopqrstuvwxy"], ["abcdefghijklmnopqrstuvwxyz", "bcdefghijklmnopqrstuvwxyzr"], ["abcdefghijklmnopqrstuvwxyz", "bcdefghijklmnopqrstuvwxyzq"]]
                [null, true, false, true, false, true, true]
                */)
        );
    }

}
