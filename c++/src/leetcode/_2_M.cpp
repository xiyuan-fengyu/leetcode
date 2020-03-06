class ListNode {
    int val;
    ListNode* next;
    ListNode(int x) : val(x), next(nullptr) {}
}

class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode tempNode(0);
        ListNode* curSum = &tempNode;
        ListNode* cur1 = l1;
        ListNode* cur2 = l2;
        int additionCarry = 0;
        while (true) {
            int breakFlag = 0;
            if (cur1 != nullptr) {
                additionCarry += cur1->val;
                cur1 = cur1->next;
            }
            else {
                breakFlag++;
            }
            if (cur2 != nullptr) {
                additionCarry += cur2->val;
                cur2 = cur2->next;
            }
            else {
                breakFlag++;
            }
            if (breakFlag == 2 && additionCarry == 0) {
                break;
            }
            curSum->next = new ListNode(additionCarry % 10);
            curSum = curSum->next;
            additionCarry /= 10;
        }
        return tempNode.next;
    }
};
