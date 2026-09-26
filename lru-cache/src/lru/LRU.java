package lru;

import java.util.Map;

import node.Node;
import dll.DoublyLinkedList;

public class LRU<K, V> {
    private int capacity;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.map = new java.util.HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    public synchronized V get(K key) {
        if(!map.containsKey(key)) return null;
        Node<K, V> node = map.get(key);
        dll.remove(node);
        dll.addToFront(node);
        return node.getValue();
    }

    public synchronized void put(K key, V value) {
        if(map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            dll.remove(node);
            dll.addToFront(node);
        } else {
            if(map.size() >= capacity) {
                Node<K, V> lastNode = dll.removeLast();
                if(lastNode != null) {
                    map.remove(lastNode.getKey());
                }
            }
            Node<K, V> newNode = new Node<>(key, value);
            dll.addToFront(newNode);
            map.put(key, newNode);
        }
    }
}
