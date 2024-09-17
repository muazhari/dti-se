package org.dti.se.module1session11;

public class DoublyLinkedList<T extends Comparable<T>> {
    protected Node<T> head;
    protected Node<T> tail;
    protected Integer size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Integer getSize() {
        return size;
    }

    public Integer indexOf(T data) throws NullPointerException {
        Integer currentIndex = 0;
        Node<T> current = head;
        while (true) {
            if (current == null) {
                throw new NullPointerException("Node not found.");
            }

            if (current.getData().equals(data)) {
                return currentIndex;
            }

            current = current.getNext();
            currentIndex++;
        }
    }

    public T selectAtIndex(Integer index) throws IndexOutOfBoundsException {
        return selectNodeAtIndex(index).getData();
    }

    private Node<T> selectNodeAtIndex(Integer index) throws IndexOutOfBoundsException {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        } else if (index.equals(0)) {
            return head;
        } else if (index.equals(size - 1)) {
            return tail;
        } else {
            Integer currentIndex = 0;
            Node<T> current = head;
            while (true) {
                if (current == null) {
                    throw new IndexOutOfBoundsException();
                }

                if (currentIndex.equals(index)) {
                    return current;
                }

                current = current.getNext();
                currentIndex++;
            }
        }
    }

    public void insertAtIndex(T data, Integer index) throws IndexOutOfBoundsException {
        Node<T> newNode = new Node<>(data);
        if (size == 0 && index.equals(0)) {
            head = newNode;
            tail = newNode;
        } else if (index.equals(0)) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else if (index.equals(size)) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        } else {
            Node<T> current = selectNodeAtIndex(index);
            Node<T> prev = current.getPrev();
            prev.setNext(newNode);
            newNode.setPrev(prev);
            newNode.setNext(current);
            current.setPrev(newNode);
        }
        ++size;
    }

    public void deleteAtIndex(Integer index) throws IndexOutOfBoundsException {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        } else if (index.equals(0)) {
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            }
        } else if (index.equals(size - 1)) {
            tail = tail.getPrev();
            if (tail != null) {
                tail.setNext(null);
            }
        } else {
            Node<T> current = selectNodeAtIndex(index);
            Node<T> prev = current.getPrev();
            Node<T> next = current.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }
        --size;
    }

}
