package org.dti.se.module1session11;

public class Main {
    public static void main(String[] args) throws Exception {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        doublyLinkedList.insertAtIndex(0, 0);
        doublyLinkedList.insertAtIndex(1, 1);
        doublyLinkedList.insertAtIndex(2, 2);
        assert doublyLinkedList.getSize() == 3;

        assert doublyLinkedList.indexOf(0) == 0;
        assert doublyLinkedList.indexOf(1) == 1;
        assert doublyLinkedList.indexOf(2) == 2;

        assert doublyLinkedList.selectAtIndex(0) == 0;
        assert doublyLinkedList.selectAtIndex(1) == 1;
        assert doublyLinkedList.selectAtIndex(2) == 2;

        doublyLinkedList.deleteAtIndex(0);
        doublyLinkedList.deleteAtIndex(0);
        doublyLinkedList.deleteAtIndex(0);
        assert doublyLinkedList.getSize() == 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        assert stack.getSize() == 3;

        assert stack.pop() == 2;
        assert stack.pop() == 1;
        assert stack.pop() == 0;
        assert stack.getSize() == 0;

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        assert queue.getSize() == 3;

        assert queue.dequeue() == 0;
        assert queue.dequeue() == 1;
        assert queue.dequeue() == 2;
        assert queue.getSize() == 0;

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.insert(10);
        binarySearchTree.insert(2);
        binarySearchTree.insert(20);
        binarySearchTree.insert(1);
        binarySearchTree.insert(3);
        binarySearchTree.insert(15);
        binarySearchTree.insert(30);
        assert binarySearchTree.getSize() == 7;

        assert binarySearchTree.isFound(10);
        assert binarySearchTree.isFound(1);
        assert binarySearchTree.isFound(30);

        DoublyLinkedList<Integer> collected = binarySearchTree.collectInOrder();
        assert collected.selectAtIndex(0) == 1;
        assert collected.selectAtIndex(1) == 2;
        assert collected.selectAtIndex(2) == 3;
        assert collected.selectAtIndex(3) == 10;
        assert collected.selectAtIndex(4) == 15;
        assert collected.selectAtIndex(5) == 20;
        assert collected.selectAtIndex(6) == 30;

        binarySearchTree.delete(1);
        binarySearchTree.delete(2);
        binarySearchTree.delete(3);
        binarySearchTree.delete(10);
        binarySearchTree.delete(15);
        binarySearchTree.delete(20);
        binarySearchTree.delete(30);
        assert binarySearchTree.getSize() == 0;
    }
}