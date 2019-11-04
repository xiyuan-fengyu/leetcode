package _408试题;

import com.xiyuan.templateString.EnableTemplateString;
import leetcode.Tester;
import sun.awt.image.ImageWatched;
import sun.swing.plaf.synth.DefaultSynthStyle;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/11/4 10:50.
 */
@EnableTemplateString
public class _2012_ComSubLinkNodes {

    static class LinkNode {

        public char data;

        public LinkNode next;

        public LinkNode(char data) {
            this.data = data;
        }

        public static LinkNode build(String str) {
            if (str == null || str.isEmpty()) {
                return null;
            }
            LinkNode head = new LinkNode(str.charAt(0));
            LinkNode cur = head;
            for (int i = 1, len = str.length(); i < len; i++) {
                LinkNode newNode = new LinkNode(str.charAt(i));
                cur.next = newNode;
                cur = newNode;
            }
            return head;
        }

        public LinkNode tail() {
            LinkNode tail = this.next;
            while (tail.next != null) {
                tail = tail.next;
            }
            return tail;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            LinkNode cur = this;
            while (cur != null) {
                sb.append(cur.data);
                cur = cur.next;
            }
            return sb.toString();
        }
    }

    class Solution {

        public LinkNode comSub(LinkNode first, LinkNode second) {
            LinkNode cur = first;
            int len1 = 0;
            while (cur != null) {
                len1++;
                cur = cur.next;
            }

            cur = second;
            int len2 = 0;
            while (cur != null) {
                len2++;
                cur = cur.next;
            }

            LinkNode p1 = first;
            LinkNode p2 = second;
            if (len1 > len2) {
                for (int i = 0, len = len1 - len2; i < len; i++) {
                    p1 = p1.next;
                }
            }
            else if (len1 < len2) {
                for (int i = 0, len = len2 - len1; i < len; i++) {
                    p2 = p2.next;
                }
            }

            do {
                if (p1 == p2) {
                    return p1;
                }
                p1 = p1.next;
                p2 = p2.next;
            } while (p1 != null);
            return null;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                null,
                (solution, methodName, params) -> {
                    int comSuffixLen = 0;
                    String str1 = (String) params[0];
                    String str2 = (String) params[1];
                    for (int i = str1.length() - 1, j = str2.length() - 1; i > -1 && j > -1; i--, j--) {
                        if (str1.charAt(i) == str2.charAt(j)) {
                            comSuffixLen++;
                        }
                        else break;
                    }
                    LinkNode suffix = comSuffixLen == 0 ? null : LinkNode.build(str1.substring(str1.length() - comSuffixLen));
                    LinkNode link1 = LinkNode.build(str1.substring(0, str1.length() - comSuffixLen));
                    link1.tail().next = suffix;
                    LinkNode link2 = LinkNode.build(str2.substring(0, str2.length() - comSuffixLen));
                    link2.tail().next = suffix;
                    return new LinkNode[] {
                            link1,
                            link2
                    };
                },
                (solution, methodName, res) -> res == null ? "" : res.toString(),
                r(/*
                ["Solution", "comSub"]
                [[], ["loading", "being"]]
                [null, "ing"]
                */)
        );
    }

}
