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
     * 解题思路 http://note.youdao.com/noteshare?id=214f587d7e7fa79cdd5fdaf39ba0630c&sub=36456EC3F53C47A29F8EA0D90177B2E4
     */
    class Solution {

        public boolean canConvert(String str1, String str2) {
            int len = str1.length();
            if (len != str2.length()) {
                return false;
            }
            if (len <= 1) {
                return true;
            }

            char[] chars1 = str1.toCharArray();
            char[] chars2 = str2.toCharArray();

            // 迁移表
            char[] table = new char['z' + 1];
            // 第一次更新为上游字母，发现第二个上游字母后，将值更新为 1，用于后面用来判定环上是否存在上游侧链
            char[] upperReaches = new char['z' + 1];
            // 下面 visited 用于辅助计算空闲字母数量
            boolean[] visited = new boolean['z' + 1];
            int freeCharNum = 26;
            for (int i = 0; i < len; i++) {
                char char1 = chars1[i];
                char char2 = chars2[i];

                if (!visited[char1]) {
                    visited[char1] = true;
                    freeCharNum--;
                }
                if (!visited[char2]) {
                    visited[char2] = true;
                    freeCharNum--;
                }

                if (table[char1] != 0 && table[char1] != char2) {
                    // 该字母下游分叉，不可转换
                    return false;
                }
                else {
                    // 更新 char2 的上游字母情况
                    char upperReach = upperReaches[char2];
                    if (upperReach == 0) {
                        upperReaches[char2] = char1;
                    }
                    else if (upperReach >= 'a' && upperReach != char1) {
                        // 有上游侧链
                        upperReaches[char2] = 1;
                    }
                    table[char1] = char2;
                }
            }

            // 检查是否存在环
            // 下面 visited 用于辅助记录哪些字母是访问过的
            Arrays.fill(visited, false);
            for (char i = 'a'; i <= 'z'; i++) {
                if (visited[i]) {
                    continue;
                }
                // 检测环的过程中记录是否有上游侧链
                boolean hasUpperSideLine = false;
                boolean hasCircle = false;
                char nextC = i;
                // 检查是否有环
                while ((nextC = table[nextC]) != 0) {
                    if (visited[nextC]) {
                        // 已检查过的节点，认为可以走通转换流程
                        break;
                    }

                    if (upperReaches[nextC] == 1)  {
                        hasUpperSideLine = true;
                    }

                    if (nextC == i) {
                        // 有环
                        hasCircle = true;
                        break;
                    }

                    visited[nextC] = true;
                }
                visited[i] = true;

                if (hasCircle) {
                    // 有环
                    if (hasUpperSideLine || freeCharNum > 0) {
                        // 存在上游侧链 或 有空闲字母，可以转换
                    }
                    else {
                        return false;
                    }
                }
            }

            return true;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "canConvert", "canConvert", "canConvert", "canConvert", "canConvert"]
                [[], ["aabcc", "ccdee"], ["leetcode", "codeleet"], ["abcdefghijklmnopqrstuvwxyz", "zabcdefghijklmnopqrstuvwxy"], ["abcdefghijklmnopqrstuvwxyz", "bcdefghijklmnopqrstuvwxyzr"], ["abcdefghijklmnopqrstuvwxyz", "bcdefghijklmnopqrstuvwxyzq"]]
                [null, true, false, false, true, true]
                */)
        );
    }

}
