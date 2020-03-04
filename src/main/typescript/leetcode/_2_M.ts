class ListNode {
    val: number;
    next: ListNode;
    constructor(val: number) {
        this.val = val;
    }
}

function addTwoNumbers(l1: ListNode, l2: ListNode): ListNode {
    const tempHead = new ListNode(0);
    let curSum = tempHead;
    let cur1 = l1;
    let cur2 = l2;
    let additionCarry = 0;
    while (true) {
        let nulls = 0;
        if (cur1 != null) {
            additionCarry += cur1.val;
            cur1 = cur1.next;
        }
        else {
            nulls++;
        }
        if (cur2 != null) {
            additionCarry += cur2.val;
            cur2 = cur2.next;
        }
        else {
            nulls++;
        }

        if (nulls == 2 && additionCarry == 0) {
            break;
        }

        curSum.next = new ListNode(additionCarry % 10);
        curSum = curSum.next;
        additionCarry = Math.floor(additionCarry / 10);
    }
    return tempHead.next;
}
