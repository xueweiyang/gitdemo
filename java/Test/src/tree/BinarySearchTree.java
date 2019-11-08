package tree;

import stack.Log;

public class BinarySearchTree {

    static Node head;

    public static void main(String[] args) {
        int[] data = {12, 8, 9, 6, 7, 15, 23, 19, 33};
        for (int value : data) {
            insert(value);
        }
        printPrev(head);
    }

    static boolean delete(int val) {
        if (head == null) {
            return false;
        }
        Node tmp = head;
        Node fa = null;
        while (tmp != null && tmp.val != val) {
            fa = tmp;
            if (tmp.val > val) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        if (tmp == null) {
            return false;
        }
        if (tmp.left != null && tmp.right != null) {
            Node ins = tmp.right;
            Node insFa = null;
            while (ins.left != null) {
                insFa = ins;
                ins = ins.left;
            }
            tmp.val = ins.val;
            if (insFa == null) {
                tmp.right = null;
            } else if (ins == insFa.left) {
                insFa.left = null;
            } else if (ins == insFa.right) {
                insFa.right = null;
            }
            return true;
        }
        Node child = null;
        if (tmp.left != null) {
            child = tmp.left;
        } else if (tmp.right != null) {
            child = tmp.right;
        }
        if (fa == null) {
            head = child;
        } else if (fa.left == tmp) {
            fa.left = child;
        } else if (fa.right == tmp) {
            fa.right = child;
        }
        return true;
    }

    static Node find(int val) {
        if (head == null) {
            return null;
        }
        Node tmp = head;
        while (tmp != null) {
            if (tmp.val == val) {
                return tmp;
            } else if (tmp.val > val) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return null;
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
        if (node == null) {
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
