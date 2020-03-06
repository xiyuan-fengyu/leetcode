package leetcode;

import com.xiyuan.templateString.EnableTemplateString;

/**
 * Created by xiyuan_fengyu on 2020/3/4 15:25.
 * https://leetcode-cn.com/problems/add-two-numbers/
 */
@EnableTemplateString
public class _2_M {

    /*
    2. 两数相加
    给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

    如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

    您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

    示例：

    输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
    输出：7 -> 0 -> 8
    原因：342 + 465 = 807
     */
    class Solution {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode tempHead = new ListNode(0);
            ListNode curSum = tempHead;
            ListNode cur1 = l1;
            ListNode cur2 = l2;
            int additionCarry = 0;
            while (true) {
                int breakFlag = 0;
                if (cur1 != null) {
                    additionCarry += cur1.val;
                    cur1 = cur1.next;
                }
                else {
                    breakFlag++;
                }
                if (cur2 != null) {
                    additionCarry += cur2.val;
                    cur2 = cur2.next;
                }
                else {
                    breakFlag++;
                }
                if (breakFlag == 2 && additionCarry == 0) {
                    break;
                }
                else {
                    curSum.next = new ListNode(additionCarry % 10);
                    curSum = curSum.next;
                    additionCarry /= 10;
                }
            }
            return tempHead.next;
        }

    }

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

    }

}
