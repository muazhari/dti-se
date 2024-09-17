package org.dti.se.module1session11;

public class Stack<T extends Comparable<T>> extends DoublyLinkedList<T> {
    public void push(T data) {
        insertAtIndex(data, size);
    }

    public T pop() {
        T data = tail.getData();
        deleteAtIndex(size - 1);
        return data;
    }
}
