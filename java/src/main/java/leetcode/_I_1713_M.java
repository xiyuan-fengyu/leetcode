package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

import java.util.*;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2020/3/13 9:51.
 */
@EnableTemplateString
public class _I_1713_M {

    /*
https://leetcode-cn.com/problems/re-space-lcci/
面试题 17.13. 恢复空格
哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。

注意：本题相对原题稍作改动，只需返回未识别的字符数



示例：

输入：
dictionary = ["looked","just","like","her","brother"]
sentence = "jesslookedjustliketimherbrother"
输出： 7
解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
提示：

0 <= len(sentence) <= 1000
dictionary中总字符数不超过 150000。
你可以认为dictionary和sentence中只包含小写字母。
     */

    // set + 动态规划
    class Solution_v1 {
        public int respace(String[] dictionary, String sentence) {
            int len = sentence.length();
            if (len == 0) {
                return 0;
            }

            Set<String> dicSet = new HashSet<>(Arrays.asList(dictionary));
            int[] dp = new int[len + 1];
            for (int i = 1; i <= len; i++) {
                int minUnknown = i;
                for (int j = i - 1; j > -1; j--) {
                    String suffix = sentence.substring(j, i);
                    if (dicSet.contains(suffix)) {
                        minUnknown = Math.min(minUnknown, dp[j]);
                    }
                    else {
                        minUnknown = Math.min(minUnknown, i - j + dp[j]);
                    }
                }
                dp[i] = minUnknown;
            }
            return dp[len];
        }
    }

    // 字典树 + 动态规划：字典树用于检测字符串是否在字典中
    class Solution_v2 {

        class Node {
            char val;
            boolean end;
            Node[] children;

            Node(char val, boolean end) {
                this.val = val;
                this.end = end;
            }

            Node addChild(char val, boolean end) {
                if (this.children == null) {
                    this.children = new Node[26];
                }

                int index = val - 'a';
                Node nextNode = this.children[index];
                if (nextNode == null) {
                    this.children[val - 'a'] = nextNode = new Node(val, end);
                }
                else {
                    nextNode.end |= end;
                }
                return nextNode;
            }

            Node next(char val) {
                return this.children != null ? this.children[val - 'a'] : null;
            }
        }

        class DicTree {

            private final Node root = new Node('\0', false);

            DicTree(String[] words) {
                addWords(words);
            }

            void addWord(String word) {
                Node curNode = root;
                for (int i = 0, len = word.length(); i < len; i++) {
                    curNode = curNode.addChild(word.charAt(i), i + 1 == len);
                }
            }

            void addWords(String[] words) {
                for (String word : words) {
                    addWord(word);
                }
            }

            boolean exist(String str, int from, int to) {
                Node curNode = root;
                for (int i = from; i < to; i++) {
                    curNode = curNode.next(str.charAt(i));
                    if (curNode == null) {
                        return false;
                    }
                    else if (i + 1 == to) {
                        return curNode.end;
                    }
                }
                return false;
            }

        }

        public int respace(String[] dictionary, String sentence) {
            int len = sentence.length();
            if (len == 0) {
                return 0;
            }

            DicTree dicTree = new DicTree(dictionary);
            int[] dp = new int[len + 1];
            for (int i = 1; i <= len; i++) {
                int minUnknown = i;
                for (int j = i - 1; j > -1; j--) {
                    if (dicTree.exist(sentence, j, i)) {
                        minUnknown = Math.min(minUnknown, dp[j]);
                    }
                    else {
                        minUnknown = Math.min(minUnknown, i - j + dp[j]);
                    }
                }
                dp[i] = minUnknown;
            }
            return dp[len];
        }
    }

    // python 中这种算法表现比较好
    class Solution_v3 {
        private boolean substrEquals(String str, int from, String other) {
            for (int i = 0, len = other.length(); i < len; i++) {
                if (other.charAt(i) != str.charAt(from + i)) {
                    return false;
                }
            }
            return true;
        }

        public int respace(String[] dictionary, String sentence) {
            int len = sentence.length();
            if (len == 0) {
                return 0;
            }
            Map<Character, List<String>> dic = new HashMap<>();
            for (String s : dictionary) {
                Character c = s.charAt(0);
                List<String> words = dic.get(c);
                if (words == null) {
                    dic.put(c, words = new ArrayList<>());
                }
                words.add(s);
            }

            int sLen = sentence.length();
            int[] dp = new int[sLen + 1];
            for (int i = 0; i <= sLen; i++) {
                dp[i] = i;
            }

            for (int i = 0; i < sLen; i++) {
                List<String> words = dic.get(sentence.charAt(i));
                if (words != null) {
                    for (String word : words) {
                        int wLen = word.length();
                        if (i + wLen <= sLen && substrEquals(sentence, i, word)) {
                            dp[i + wLen] = Math.min(dp[i + wLen], dp[i]);
                        }
                    }
                }
                dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);
            }
            return dp[sLen];
        }
    }

    // 字典树 + 动态规划：根据字符，在字典的节点中移动，记录未匹配的字符数
    class Solution_v4 {

        class Node {
            char val;
            boolean end;
            Node[] children;

            Node(char val, boolean end) {
                this.val = val;
                this.end = end;
            }

            Node addChild(char val, boolean end) {
                if (this.children == null) {
                    this.children = new Node[26];
                }

                int index = val - 'a';
                Node nextNode = this.children[index];
                if (nextNode == null) {
                    this.children[val - 'a'] = nextNode = new Node(val, end);
                }
                else {
                    nextNode.end |= end;
                }
                return nextNode;
            }

            Node next(char val) {
                return this.children != null ? this.children[val - 'a'] : null;
            }
        }

        class DicTree {

            private final Node root = new Node('\0', false);

            DicTree(String[] words) {
                addWords(words);
            }

            void addWord(String word) {
                Node curNode = root;
                for (int i = 0, len = word.length(); i < len; i++) {
                    curNode = curNode.addChild(word.charAt(i), i + 1 == len);
                }
            }

            void addWords(String[] words) {
                for (String word : words) {
                    addWord(word);
                }
            }

            int check(String str, int from, int[] dp) {
                if (dp[from] > -1) {
                    return dp[from];
                }

                int minUnknown = str.length() - from;
                Node cur = root;
                for (int i = from, len = str.length(); cur != null && i < len; i++) {
                    cur = cur.next(str.charAt(i));
                    if (cur != null && cur.end) {
                        minUnknown = Math.min(minUnknown, check(str, i + 1, dp));
                    }
                    else {
                        minUnknown = Math.min(minUnknown, i - from + 1 + check(str, i + 1, dp));
                    }
                }
                return dp[from] = minUnknown;
            }

        }

        public int respace(String[] dictionary, String sentence) {
            int len = sentence.length();
            if (len == 0) {
                return 0;
            }

            DicTree dicTree = new DicTree(dictionary);
            int[] dp = new int[len + 1];
            Arrays.fill(dp, 0, len, -1);
            dp[len] = 0;
            return dicTree.check(sentence, 0, dp);
        }
    }

    class Solution {

        int check(List<List<Integer>> wordFound, int from, int[] dp) {
            if (dp[from] > -1) {
                return dp[from];
            }

            int minUnknown = 1 + check(wordFound, from + 1, dp);
            for (Integer wLen : wordFound.get(from)) {
                minUnknown = Math.min(minUnknown, check(wordFound, from + wLen, dp));
            }
            return dp[from] = minUnknown;
        }

        public int respace(String[] dictionary, String sentence) {
            int sLen = sentence.length();
            if (sLen == 0) {
                return 0;
            }

            List<List<Integer>> wordFound = new ArrayList<>(sLen);
            for (int i = 0; i < sLen; i++) {
                wordFound.add(new ArrayList<>());
            }
            for (String word : dictionary) {
                int wLen = word.length();
                int pos = -1;
                while ((pos = sentence.indexOf(word, pos + 1)) > -1) {
                    wordFound.get(pos).add(wLen);
                }
            }
            int[] dp = new int[sLen + 1];
            Arrays.fill(dp, 0, sLen, -1);
            dp[sLen] = 0;
            return check(wordFound, 0, dp);
        }
    }

    public static void main(String[] args) {
        Tester.test(
                r(/*
                ["Solution", "respace"]
                [[], [["looked","just","like","her","brother"], "jesslookedjustliketimherbrother"] ]
                [null, 7]
                */)
        );
    }

}
