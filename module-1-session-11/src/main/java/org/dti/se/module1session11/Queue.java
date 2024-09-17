package org.dti.se.module1session11;

public class Queue<T extends Comparable<T>> extends DoublyLinkedList<T> {
    public void enqueue(T data) {
        insertAtIndex(data, size);
    }

    public T dequeue() {
        T data = head.getData();
        deleteAtIndex(0);
        return data;
    }
}
