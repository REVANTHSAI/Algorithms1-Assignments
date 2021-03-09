package com.revanth.Week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] listArray;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        listArray = new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (listArray.length == size)
            resize(2 * listArray.length);
        listArray[size++] = item;
    }

    private void resize(int newSize) {
        Object[] newArray = new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = listArray[i];
        }
        listArray = newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(size);
        Item returnValue = (Item) listArray[randIndex];
        if (randIndex != size - 1) {
            listArray[randIndex] = listArray[size - 1];
        }
        listArray[size - 1] = null;
        size--;

        if (size > 0 && size == listArray.length / 4) {
            resize(listArray.length / 2);
        }
        return returnValue;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(size);
        Item returnValue = (Item) listArray[randIndex];
        return returnValue;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = size;
        private int[] order;

        public RandomizedQueueIterator() {
            order = new int[i];
            for (int j = 0; j < i; ++j) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return (Item) listArray[order[--i]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);

        for (Integer num : rq)
            System.out.println(num);

        System.out.println(rq.sample());

        System.out.println("****************");
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();

        for (Integer num : rq)
            System.out.println(num);


    }

}
