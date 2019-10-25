package tree;

import stack.Log;

public class BinarySearchTree {

    static Node head;

    public static void main(String[] args) {
        int[] data= {12,8,9,6,7,15,23,19,33};
        for (int value : data) {
            insert(value);
        }
        printPrev(head);
    }

    static void insert(int val) {
        if (head == null) {
            head = new Node(val);
            return;
        }
        Node tmp = head;
        while (tmp != null) {
            if (tmp.val > val) {
                if (tmp.left == null) {
                    tmp.left = new Node(val);
                    return;
                } else {
                    tmp = tmp.left;
                }
            } else {
                if (tmp.right == null) {
                    tmp.right = new Node(val);
                    return;
                } else {
                    tmp = tmp.right;
                }
            }
        }
    }

    static void printPrev(Node node) {
        if (node==null){
            return;
        }
        Log.log(node.val);
        printPrev(node.left);
        printPrev(node.right);
    }

    static Node createBinarySearchTree() {
        return null;
    }

}
