package com.example.demo.algorithm;

import java.util.Stack;

/**
 * @author ：jyk
 * @date ：Created in 2020/1/20 23:03
 * @description：
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        reverseLinkedList.test();
    }

    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.setNext(node2);
        node2.setNext(node3);
        node1 = reverseByPointer(node1);
        while (node1 != null) {
            System.out.println(node1.val);
            node1 = node1.getNext();
        }
    }

    private Node reverseByStack(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Stack<Node> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.getNext();
        }
        head = stack.pop();
        Node tmp = head;
        while (!stack.empty()) {
            Node node = stack.pop();
            node.setNext(null);
            tmp.setNext(node);
            tmp = node;
        }
        return head;
    }

    private Node reverseByRecursion(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        //递归获取当前节点的后一个节点
        Node tmp = reverseByRecursion(head.getNext());
        Node node = head.getNext();
        head.setNext(null);
        node.setNext(head);
        return tmp;
    }

    private Node reverseByPointer(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        //pre指针指向前一个节点，初始第一个节点的前节点为空
        Node pre = null;
        //tmp指针指向当前节点
        Node tmp = null;
        while (head != null) {
            //tmp指针指向head头指针节点
            tmp = head;
            //head头指针向后遍历
            head = head.getNext();
            //反转，设置当前节点的下一个节点为前一个节点
            tmp.setNext(pre);
            //pre指针向后移动，指向当前节点
            pre = tmp;
        }
        return tmp;
    }

    private class Node {
        private int val;

        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
