public class LinkReverse {

    public static void main(String[] args) {
        Node head = reverseNodeList(createNodeList(1));
        printNodeList(head);
        printNodeList(deleteNodeInLastOfPos(head, 1));
    }

    /**
     * 删除倒数第pos个节点
     * @param pos
     * @return
     */
    public static Node deleteNodeInLastOfPos(Node head, int pos) {
        int tmpPos = pos;
        if (pos < 1) {
            return head;
        }
        if (head == null) {
            return null;
        }
        Node first = head;
        Node sec = head;
        while (pos-->0) {
            sec = sec.next;
            if (sec == null) {
                break;
            }
        }
        while (sec != null && sec.next != null) {
            first = first.next;
            sec = sec.next;
        }
        log(String.format("倒数%d个节点的值是%d", tmpPos, first.next.value));
        if (first.next == null) {

        }
        first.next = first.next.next;
        return head;
    }

    /**
     * 翻转链表
     * @param head
     * @return
     */
    public static Node reverseNodeList(Node head) {
        if (head == null) {
            return null;
        }
        Node prev = head;
        Node cur = head.next;
        if (cur == null) {
            return prev;
        }
        prev.next = null;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static Node createNodeList(int num) {
        if (num == 0) {
            return null;
        }
        Node head = new Node();
        head.value = 0;
        Node tmp = head;
        for (int i = 1;i<num;i++) {
            Node curNode = new Node();
            curNode.value=i;
            head.next = curNode;
            head = curNode;
        }
        return tmp;
    }

    public static void printNodeList(Node head) {
        log("========");
        while (head != null) {
            log("value:"+head.value);
            head = head.next;
        }
        log("========");
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
