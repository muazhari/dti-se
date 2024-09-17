package org.dti.se.module1session11;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    private T data;
    private Node<T> next;
    private Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    @Override
    public int compareTo(Node<T> o) {
        if (o != null) {
            return this.getData().compareTo(o.getData());
        } else {
            throw new IllegalStateException("Data is not comparable.");
        }
    }
}
