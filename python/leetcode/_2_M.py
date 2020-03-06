class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def addTwoNumbers(self, l1: ListNode, l2: ListNode) -> ListNode:
        tempHead = ListNode(0)
        curSum = tempHead
        cur1 = l1
        cur2 = l2
        additionCarry = 0
        while 1:
            nones = 0
            if cur1 is not None:
                additionCarry += cur1.val
                cur1 = cur1.next
            else:
                nones += 1
            if cur2 is not None:
                additionCarry += cur2.val
                cur2 = cur2.next
            else:
                nones += 1

            if nones == 2 and additionCarry == 0:
                break

            curSum.next = ListNode(additionCarry % 10)
            curSum = curSum.next
            # 注意：下面一行 // 是整数除法, / 是浮点除法
            additionCarry //= 10
        return tempHead.next
