public class ListSolution{

    public ListNode reverse(ListNode head){
        ListNode pre = null;
        while(head != null) {
            ListNode tmp = head.next;
            head.next = pre;
            pre = head;
            head = tmp;
        }
        return pre;
    }

    public ListNode addList(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        int c = 0;
        int sum = 0;

        while( l1 != null || l2 != null) {
            if (l1 != null) {sum = sum + l1.value; l1 = l1.next;}
            if (l2 != null) {sum = sum + l2.value; l2 = l2.next;}
            sum += c;
            c = sum % 10;
            sum = sum / 10;
            ListNode node = new ListNode(sum);
            pre.next = node;
            pre = pre.next;
        }
        if (c == 1) { pre.next = new ListNode(1);}
        return dummy.next;
    }
}


class ListNode {
    ListNode next;
    ListNode pre;
    int value;

    public ListNode(int value) {
        this.value = value;
    }
}
