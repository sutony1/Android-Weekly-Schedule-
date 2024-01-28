package com.example.myclassmanager;

public class LinkedList {
    public Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(String data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public String get(int i) {
        Node current = head;
        int count = 0;
        while (current != null) {
            if (count == i) {
                return current.data;
            }
            count++;
            current = current.next;
        }
        return null;
    }
}
