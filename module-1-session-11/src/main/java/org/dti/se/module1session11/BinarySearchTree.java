package org.dti.se.module1session11;

public class BinarySearchTree<T extends Comparable<T>> {
    protected Node<T> root;
    protected Integer size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean isFound(T data) {
        try {
            findNode(data);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private Node<T> findNode(T data) throws NullPointerException {
        Node<T> temp = new Node<>(data);
        Node<T> current = root;
        while (true) {
            if (current == null) {
                throw new NullPointerException("Node not found.");
            }

            if (temp.compareTo(current) < 0) {
                current = current.getPrev();
            } else if (temp.compareTo(current) > 0) {
                current = current.getNext();
            } else {
                return current;
            }
        }
    }

    public void insert(T data) {
        Node<T> nodeToInsert = new Node<>(data);

        if (root == null) {
            root = nodeToInsert;
            ++size;
            return;
        }

        Node<T> parent = root;
        Node<T> current = root;
        while (true) {
            if (nodeToInsert.compareTo(current) < 0) {
                if (current.getPrev() == null) {
                    current.setPrev(nodeToInsert);
                    ++size;
                    break;
                } else {
                    parent = current;
                    current = current.getPrev();
                }
            } else if (nodeToInsert.compareTo(current) > 0) {
                if (current.getNext() == null) {
                    current.setNext(nodeToInsert);
                    ++size;
                    break;
                } else {
                    parent = current;
                    current = current.getNext();
                }
            } else {
                if (nodeToInsert.compareTo(parent) < 0) {
                    parent.setPrev(nodeToInsert);
                } else if (nodeToInsert.compareTo(parent) > 0) {
                    parent.setNext(nodeToInsert);
                } else {
                    root = nodeToInsert;
                }
                ++size;
                break;
            }
        }
    }

    public void delete(T data) throws NullPointerException {
        Node<T> temp = new Node<>(data);
        Node<T> parent = root;
        Node<T> current = root;
        while (true) {
            if (current == null) {
                throw new NullPointerException("Node not found.");
            }

            if (temp.compareTo(current) < 0) {
                parent = current;
                current = current.getPrev();
            } else if (temp.compareTo(current) > 0) {
                parent = current;
                current = current.getNext();
            } else {
                if (current.getPrev() == null && current.getNext() == null) {
                    if (temp.compareTo(parent) < 0) {
                        parent.setPrev(null);
                    } else if (temp.compareTo(parent) > 0) {
                        parent.setNext(null);
                    } else {
                        root = null;
                    }
                } else if (current.getPrev() == null) {
                    if (temp.compareTo(parent) < 0) {
                        parent.setPrev(current.getNext());
                    } else if (temp.compareTo(parent) > 0) {
                        parent.setNext(current.getNext());
                    } else {
                        root = current.getNext();
                    }
                } else if (current.getNext() == null) {
                    if (temp.compareTo(parent) < 0) {
                        parent.setPrev(current.getPrev());
                    } else if (temp.compareTo(parent) > 0) {
                        parent.setNext(current.getPrev());
                    } else {
                        root = current.getPrev();
                    }
                } else {
                    Node<T> successor = current.getNext();
                    while (successor.getPrev() != null) {
                        successor = successor.getPrev();
                    }

                    successor.setPrev(current.getPrev());

                    if (temp.compareTo(parent) < 0) {
                        parent.setPrev(successor);
                    } else {
                        parent.setNext(successor);
                    }
                }
                --size;
                break;
            }
        }
    }

    private void collectInOrder(Node<T> node, DoublyLinkedList<T> collected) {
        if (node == null) {
            return;
        }

        collectInOrder(node.getPrev(), collected);
        collected.insertAtIndex(node.getData(), collected.size);
        collectInOrder(node.getNext(), collected);
    }

    public DoublyLinkedList<T> collectInOrder() {
        DoublyLinkedList<T> collected = new DoublyLinkedList<>();
        collectInOrder(root, collected);
        return collected;
    }
}
