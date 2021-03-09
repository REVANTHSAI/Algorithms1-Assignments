package com.revanth.Week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {


    private CustomDoubleLinkedList<Item> ll;

    // construct an empty deque
    public Deque() {
        ll = new CustomDoubleLinkedList<>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return ll.getSize();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        ll.insertAtHead(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        ll.insertAtTail(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return ll.removeAtHead();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return ll.removeAtTail();
    }


    @Override
    public Iterator<Item> iterator() {
        return new customIterator(ll.getHead());
    }


    private class customIterator implements Iterator<Item> {

        CustomDoubleLinkedList.Node<Item> cNode;

        public customIterator(CustomDoubleLinkedList.Node<Item> head) {
            this.cNode = ll.getHead();
        }

        @Override
        public boolean hasNext() {
            return cNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item val = cNode.value;
            cNode = cNode.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        if (dq.isEmpty()) System.out.println(dq.isEmpty());
        dq.addFirst(1);
        dq.addFirst(1);
        dq.addFirst(1);
        dq.addLast(2);
        dq.addLast(2);
        dq.addLast(2);
        for (Integer val : dq) System.out.println("Val: " + val);

        dq.removeFirst();
        dq.removeLast();
        System.out.println("*************");
        for (Integer val : dq) System.out.println("Val: " + val);

        dq.removeFirst();
        dq.removeLast();
        System.out.println("*************");
        for (Integer val : dq) System.out.println("Val: " + val);

        dq.removeFirst();
        System.out.println("*************");
        System.out.println(dq.isEmpty());
        for (Integer val : dq) System.out.println("Val: " + val);

        dq.removeLast();
        System.out.println("*************");
        for (Integer val : dq) System.out.println("Val: " + val);

        System.out.println(dq.isEmpty());


    }
}

class CustomDoubleLinkedList<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private Integer size;

    static class Node<Item> {
        Item value;
        Node<Item> next;
        Node<Item> prev;

        Node(Item val) {
            this.value = val;
            next = null;
        }
    }

    CustomDoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void insertAtTail(Item val) {
        Node<Item> newNode = new Node<>(val);
        Node<Item> oldTail = this.tail;
        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            oldTail.next = newNode;
            newNode.prev = oldTail;
            this.tail = newNode;
        }
        size++;
    }

    public void insertAtHead(Item val) {
        Node<Item> newNode = new Node<>(val);
        Node<Item> oldHead = this.head;

        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = oldHead;
            oldHead.prev = newNode;
            this.head = newNode;
        }
        size++;
    }

    public Item removeAtTail() {
        Node<Item> oldTail = tail;
        if (isEmpty()) return null;
        else if (size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = tail.prev;
            this.tail.next = null;
        }
        size--;
        return oldTail.value;
    }


    public Item removeAtHead() {
        Node<Item> oldHead = head;
        if (isEmpty()) return null;
        else if (size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = head.next;
            this.head.prev = null;
        }
        size--;
        return oldHead.value;
    }


    public Integer getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<Item> getHead() {
        return head;
    }

    public void setHead(Node<Item> head) {
        this.head = head;
    }

    public Node<Item> getTail() {
        return tail;
    }

    public void setTail(Node<Item> tail) {
        this.tail = tail;
    }
}