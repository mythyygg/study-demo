
package myth;

import java.util.HashMap;

/**
 * @description:
 * @author: yuang gang
 * @create: 2021-02-27 15:33
 **/
public class LRUCache<K, V> {

  private final float DEFAULT_LOAD_FACTOR = 0.75f;
  private int capacity;
  private HashMap<K, Node<K, V>> map;
  private Node<K, V> head;
  private Node<K, V> end;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    map = new HashMap<>((int) (capacity / DEFAULT_LOAD_FACTOR) + 1);
  }

  static class Node<K, V> {

    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  public V get(K key) {
    if (map.containsKey(key)) {
      Node<K, V> n = map.get(key);
      delete(n);
      setHead(n);
      return n.value;
    }
    return null;
  }

  public void set(K key, V value) {
    if (map.containsKey(key)) {
      // update the old value
      Node<K, V> old = map.get(key);
      old.value = value;
      delete(old);
      setHead(old);
    } else {
      Node<K, V> newNode = new Node<>(key, value);
      if (map.size() >= capacity) {
        map.remove(end.key);
        delete(end);
        setHead(newNode);
      } else {
        setHead(newNode);
      }
      map.put(key, newNode);
    }
  }

  /* This method will delete node */
  public void delete(Node<K, V> node) {
    if (node.prev != null) {
      node.prev.next = node.next;
    } else {
      head = node.next;
    }

    if (node.next != null) {
      node.next.prev = node.prev;
    } else {
      end = node.prev;
    }

  }

  /* This method will make passed node as head */
  public void setHead(Node<K, V> node) {
    node.next = head;
    node.prev = null;

    if (head != null) {
      head.prev = node;
    }
    head = node;

    if (end == null) {
      end = head;
    }
  }

  public static void main(String[] args) {
    LRUCache<Integer, Integer> lrucache = new LRUCache<>(4);
    lrucache.set(1, 100);
    lrucache.set(10, 99);
    lrucache.set(15, 98);
    lrucache.set(18, 95);
    lrucache.set(1, 94);
    System.out.println(lrucache.get(1));
    System.out.println(lrucache.get(10));
    System.out.println(lrucache.get(15));
  }
}