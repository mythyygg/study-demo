

public class StringSolution {

    public static void main(String[] args) {
        System.out.println(reverse("abfcd"));
        System.out.println(addStrings("0", "0"));
    }

    //反转字符串
    public static String reverse(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        char[] ca = new char[s.length()];
        int len = s.length();
        for (int i = 0 ; i < (s.length()+1)/2; i++){
            ca[i] = s.charAt(len-i-1);
            ca[len-i-1] = s.charAt(i);

        }

        return ca.toString();
    }

    //大数相加
    public static String addStrings(String s1, String s2) {
        int l1 = s1.length()-1;
        int l2 = s2.length()-1;
        int c = 0;
        StringBuilder sb = new StringBuilder();
        while ( l1 >= 0 || l2 >= 0) {
            if (l1 >= 0) {
                c = s1.charAt(l1) - '0' + c;
                l1--;
            }
            if (l2 >= 0) {
                c = s2.charAt(l2) - '0' + c;
                l2--;
            }
            sb.append( c % 10);
            c = c / 10;
        }
        if( c == 1) sb.append(1);
        return sb.reverse().toString();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        while(l1 != null && l2 != null) {
            if(l1.value < l2.value) {
                pre.next = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
        if(l1 != null) {
            pre.next = l1;
        }
        if(l2 != null) {
            pre.next = l2;
        }
        return dummy.next;
    }

    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast ) return true;
        }
        return false;
    }
}
