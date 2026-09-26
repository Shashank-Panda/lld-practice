import lru.LRU;
public class App {
    public static void main(String[] args) throws Exception {
        LRU<Integer, String> cache = new LRU<Integer, String>(3);

        // Test put and get operations
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println("Get key 2: " + cache.get(2)); // Should return "Two"
        System.out.println("Get key 1: " + cache.get(1)); // Should return "One"

        // Adding a new item should evict the least recently used item (key 3)
        cache.put(4, "Four");
        System.out.println("Get key 3 (should be -1): " + cache.get(3)); // Should return -1

        // Accessing key 2 again to make it most recently used
        System.out.println("Get key 2: " + cache.get(2)); // Should return "Two"

        // Adding another item should evict the least recently used item (key 1)
        cache.put(5, "Five");
        System.out.println("Get key 1 (should be -1): " + cache.get(1)); // Should return -1
    }
}

/*
1. The LRU cache should support the following operations:
2. put(key, value): Insert a key-value pair into the cache. If the cache is at capacity, remove the least recently used item before inserting the new item.
3. get(key): Get the value associated with the given key. If the key exists in the cache, move it to the front of the cache (most recently used) and return its value. If the key does not exist, return -1.
4. The cache should have a fixed capacity, specified during initialization.
5. The cache should be thread-safe, allowing concurrent access from multiple threads.
6. The cache should be efficient in terms of time complexity for both put and get operations, ideally O(1).
*/