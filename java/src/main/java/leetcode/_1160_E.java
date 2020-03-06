package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.Arrays;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/8/18 11:51.
 * https://leetcode-cn.com/contest/weekly-contest-150/problems/find-words-that-can-be-formed-by-characters/
 */
@EnableTemplateString
public class _1160_E {

    /*
    1160. 拼写单词  显示英文描述
    给你一份『词汇表』（字符串数组） words 和一张『字母表』（字符串） chars。
    假如你可以用 chars 中的『字母』（字符）拼写出 words 中的某个『单词』（字符串），那么我们就认为你掌握了这个单词。
    注意：每次拼写时，chars 中的每个字母都只能用一次。
    返回词汇表 words 中你掌握的所有单词的 长度之和。

    示例 1：
    输入：words = ["cat","bt","hat","tree"], chars = "atach"
    输出：6
    解释：
    可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。

    示例 2：
    输入：words = ["hello","world","leetcode"], chars = "welldonehoneyr"
    输出：10
    解释：
    可以形成字符串 "hello" 和 "world"，所以答案是 5 + 5 = 10。

    提示：
    1 <= words.length <= 1000
    1 <= words[i].length, chars.length <= 100
    所有字符串中都仅包含小写英文字母
     */

    class Solution {

        public int countCharacters(String[] words, String chars) {
            int[] charCounts = new int['z' - 'a' + 1];
            for (char c : chars.toCharArray()) {
                charCounts[c - 'a']++;
            }
            int res = 0;
            int charsLen = chars.length();
            for (String word : words) {
                if (word.length() > charsLen) {
                    continue;
                }
                int[] tempCounts = Arrays.copyOf(charCounts, charCounts.length);
                boolean containsAllChar = true;
                for (char c : word.toCharArray()) {
                    if (--tempCounts[c - 'a'] < 0) {
                        containsAllChar = false;
                        break;
                    }
                }
                if (containsAllChar) {
                    res += word.length();
                }
            }
            return res;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "countCharacters", "countCharacters"]
                [[], [["cat","bt","hat","tree"], "atach"], [["hello","world","leetcode"],"welldonehoneyr"]]
                [null, 6, 10]
                */)
        );
    }

}
