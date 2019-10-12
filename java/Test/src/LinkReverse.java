public class LinkReverse {

    public static void main(String[] args) {
//        Node head = createNodeList(5);
//        printNodeList(head);
//        printNodeList(deleteNodeInLastOfPos(head, 2));
        Node ringNode = createRingNodeList(5,1);
        log("是否有环:"+hasRing(ringNode));
    }

    /**
     * 判断链表是否有环
     *
     * @param head
     * @return
     */
    public static boolean hasRing(Node head) {
        if (head == null) {
            return false;
        }
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除倒数第pos个节点,添加哨兵
     *
     * @param pos
     * @return
     */
    public static Node deleteNodeInLastOfPos(Node head, int pos) {
        if (pos <= 0) {
            return head;
        }
        Node tmp = new Node();
        tmp.next = head;
        Node first = tmp;
        Node sec = tmp;
        while (pos-- > 0) {
            first = first.next;
            if (first == null) {
                return head;
            }
        }
        while (first.next != null) {
            first = first.next;
            sec = sec.next;
        }
        sec.next = sec.next.next;
        return tmp.next;
    }

    /**
     * 翻转链表
     *
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

    /**
     * 创建有环的链表
     * @param num
     * @return
     */
    public static Node createRingNodeList(int num,int ringPos) {
        if (num < 2 || ringPos > num) {
            return null;
        }
        Node ringNode = null;
        Node head = new Node();
        head.value = 0;
        Node tmp = head;
        if (ringPos == 0) {
            ringNode = tmp;
        }
        for (int i = 1; i < num; i++) {
            Node curNode = new Node();
            curNode.value = i;
            head.next = curNode;
            if (ringPos == i) {
                ringNode = curNode;
            }
            head = curNode;
        }
        if (ringNode != null) {
            head.next = ringNode;
        }
        return tmp;
    }

    public static Node createNodeList(int num) {
        if (num == 0) {
            return null;
        }
        Node head = new Node();
        head.value = 0;
        Node tmp = head;
        for (int i = 1; i < num; i++) {
            Node curNode = new Node();
            curNode.value = i;
            head.next = curNode;
            head = curNode;
        }
        return tmp;
    }

    public static void printNodeList(Node head) {
        log("========");
        while (head != null) {
            log("value:" + head.value);
            head = head.next;
        }
        log("========");
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
