package dll;

import node.Node;

public class DoublyLinkedList<K, V> {
    private final Node<K, V> head;
    private final Node<K, V> tail;

    public DoublyLinkedList() {
        this.head = new Node<>(null, null); // Dummy head
        this.tail = new Node<>(null, null); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public void addToFront(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public Node<K, V> removeLast() {
        if (tail.prev == head) {
            return null; // List is empty
        }
        Node<K, V> lastNode = tail.prev;
        remove(lastNode);
        return lastNode;
    }
    
}
