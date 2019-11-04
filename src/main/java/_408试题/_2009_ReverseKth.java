package _408试题;

import com.xiyuan.templateString.EnableTemplateString;
import leetcode.Tester;

import static com.xiyuan.templateString.TemplateString.S.r;

/**
 * Created by xiyuan_fengyu on 2019/11/4 14:04.
 */
@EnableTemplateString
public class _2009_ReverseKth {

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

        public LinkNode reverseKth(LinkNode head, int rKth) {
            LinkNode fast = head;
            while (fast != null && rKth > 0) {
                fast = fast.next;
                rKth--;
            }
            if (rKth != 0) {
                return null;
            }

            LinkNode slow = head;
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }

    }

    public static void main(String[] args) {
        Tester.test(
                null,
                (solution, methodName, params) ->  new Object[] {
                        LinkNode.build((String) params[0]),
                        params[1]
                },
                (solution, methodName, res) -> res == null ? "" : res.toString(),
                r(/*
                ["Solution", "reverseKth"]
                [[], ["abcdef", 2]]
                [null, "ef"]
                */)
        );
    }

}
