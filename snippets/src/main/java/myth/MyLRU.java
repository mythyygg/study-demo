package myth;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyLRU<K, V> {

  /*
   * 该类对象，保证该类对象在服务器上只有一份。
   */
  private static MyLRU instance;
  private static ReadWriteLock rwl = new ReentrantReadWriteLock();// 用于修改缓存中数据的读写锁
  private static ReadWriteLock rwlInstance = new ReentrantReadWriteLock();// 用于创建对象的读写锁
  private int cacheSize;// 缓存的长度
  private HashMap<K, Entry<K, V>> nodes;// 缓存容器，node的建其实就是Entry的键
  private int currentSize;// 缓存中的元素长度
  private Entry<K, V> head;// 链表的头部
  private Entry<K, V> tail;// 链表的尾部

  /*
   * 虽然getLruThread(..)方法中加了rwl.writeLock().lock();，为了以防万一，该方法中也要加锁。
   * 这样不会造成死锁，属于可重入锁，这两个锁的对象是相同的，就不会重新申请锁了。
   */
  private MyLRU(int cacheSize) {
    rwlInstance.writeLock().lock();
    try {
      this.cacheSize = cacheSize;
      currentSize = 0;
      nodes = new HashMap<>(cacheSize);// 根据提供的参数初始化容器初始化容器
    } finally {
      rwlInstance.writeLock().unlock();
    }
  }

  public static MyLRU getLruThread() {
    return getLruThread(16);
  }

  public static MyLRU getLruThread(int cacheSize) {
    rwl.readLock().lock();
    try {
      /*
       * 如果这个对象是空的，那么就关闭这个read锁，打开写锁，去获取这个对象
       */
      if (instance == null) {
        rwl.readLock().unlock();// 关闭读锁
        rwlInstance.writeLock().lock();
        ;// 打开写锁

        /*
         * 再次判断instance对象是否为空
         * 在多线程情况下，当多个线程同时读数据，发现instance是null，则read锁关闭，写锁打开，只有一个线程进来了，
         * 获取到对象后，写锁关闭，等待在外面的线程就依次获取到这个写锁，进来，如果不做判断，还会重新获取数据，这样显然没有必要。 所以必须要加这个判断。
         */
        if (instance == null) {
          // 获取instance对象
          instance = new MyLRU(cacheSize);
        }
        /*
         * 这两行的顺序无所谓，因为写锁中可以去读数据，写锁锁住的情况下说明只有当前线程持有该锁， 其他线程不能去读，也不能去写，读数据不会引起并发，所以可以写中读。
         *
         * 但是读锁中不能打开写锁，如果读锁中有写锁，那么写锁会修改数据，而此时多个线程在同时读数据，那么就会出现并发问题
         */
        rwl.readLock().lock();// 打开读锁
        rwlInstance.writeLock().unlock();
        return instance;

      }
    } finally {
      rwl.readLock().unlock();// 关闭读锁
    }
    return instance;
  }

  public Entry<K, V> get(K key) {
    rwl.readLock().lock();
    try {
      Entry<K, V> node = nodes.get(key);
      if (node != null) {
        rwl.readLock().unlock();
        rwl.writeLock().lock();
        if (node != head) {
          moveToHead(node);
        }
        rwl.writeLock().unlock();
        rwl.readLock().lock();
        return node;
      } else {
        return null;
      }
    } finally {
      rwl.readLock().unlock();
    }
  }

  public void put(K key, V value) {
    rwl.writeLock().lock();
    try {
      Entry<K, V> nd = nodes.get(key);

      if (nd == null) {
        // 判断缓存容器的大小
        // 容器空的时候
        if (currentSize == 0) {
          nd = new Entry<K, V>(tail, null, key, value);
          head = nd;// 让该节点成为头节点
          tail = nd;// 让该节点成为尾节点
          currentSize++;
        } else if (cacheSize == currentSize) {// 容器满的时候
          // 因为尾节点的点击量最小，所以要将尾节点从链表中移除
          nodes.remove(tail.key);// HashMap中移除链表的尾节点
          removeLast();// 移除最后一个
          nd = new Entry<K, V>(tail, null, key, value);
        } else {
          // 实际长度加1
          currentSize++;
          nd = new Entry<K, V>(tail, null, key, value);
        }
      } else {
        nd.value = value;// 覆盖节点中的值
      }

      // 将节点移动到缓存链的最前面
      moveToHead(nd);
      // 将节点加入到缓存中
      nodes.put(key, nd);
    } finally {
      rwl.writeLock().unlock();
    }
  }

  // 根据key值删除数据，该数据只在链满的时候才删除。
  // 删除链表中的数据，只用将链表前后两个节点连接就好
  public void remove(K key) {
    rwl.writeLock().lock();
    try {
      Entry<K, V> node = nodes.get(key);
      if (node != null) {
        if (node == head) {
          head.next.pre = null;
          head = node.next;
        }
        if (node == tail) {
          tail.pre.next = null;
          tail = tail.pre;
        }
        if (node.pre != null) {
          node.pre.next = node.next;
        }
        if (node.next != null) {
          node.next.pre = node.pre;
        }
        node = null;
      }

      nodes.remove(key);// 删除hashtable中的链
    } finally {
      rwl.writeLock().unlock();
    }
  }

  // 移除双向链表的尾节点
  private void removeLast() {
    rwl.writeLock().lock();
    try {
      if (tail != null) {
        // 判断链表是不是只有一个节点
        if (tail.pre == null) {
          head = null;
        } else {
          tail.pre.next = null;
        }
        tail = tail.pre;
      }
    } finally {
      rwl.writeLock().unlock();
    }
  }

  // 输出双链中的内容
  public void sop() {
    rwl.readLock().lock();
    try {
      for (Entry<K, V> node = head; node != null; node = node.next) {
        System.out.println("[" + node.key + " = " + node.value + "]");
      }
    } finally {
      rwl.readLock().unlock();
    }
  }

  // 将节点移动到最前面
  private void moveToHead(Entry<K, V> node) {
    rwl.writeLock().lock();
    try {
      // node节点就是头结点
      if (node == head) {
        return;
      }
      // node节点是最后一个节点
      if (node == tail) {
        // 让node的前一个节点的next指向null，并让前一个节点变为tail。
        node.pre.next = null;
        tail = node.pre;
      }
      // node节点前面有元素
      if (node.pre != null) {
        // 更改node的前一个节点的next的指向
        node.pre.next = node.next;
      }
      // node前面有元素
      if (node.next != null) {
        // 更改node的下一个节点的pre的指向
        node.next.pre = node.pre;
      }
      // 将node节点变为头结点
      if (null != head) {
        node.next = head;
        head.pre = node;
      }

      node.pre = null;
      head = node;
      // 只有一个节点
      if (tail == null) {
        tail = node;
      }
    } finally {
      rwl.writeLock().unlock();
    }
  }

  public int size() {
    rwl.readLock().lock();
    try {
      return currentSize;
    } finally {
      rwl.readLock().unlock();
    }
  }

  public void clear() {
    rwl.writeLock().lock();
    try {
      if (head != tail) {
        Entry<K, V> node = head;
        while (node != null) {
          node.value = null;
          node.pre = null;
          node = node.next;
        }

        currentSize = 0;
      }
    } finally {
      rwl.writeLock().unlock();
    }
  }

  public class Entry<K, V> {

    Entry<K, V> pre;
    Entry<K, V> next;
    K key;
    V value;

    Entry(Entry<K, V> p, Entry<K, V> next, K k, V value) {
      this.pre = p;
      this.next = next;
      this.key = k;
      this.value = value;
    }
  }

  public static void main(String[] args) {
    MyLRU<Integer, String> lru = MyLRU.getLruThread(10);

    // 添加100个数据，因为缓存空间是10，所以只要最后10个
    for (int i = 0; i < 100; i++) {
      lru.put(i, i + "--" + i);
    }
    System.out.println("第一次添加缓存的结果为：");
    lru.sop();
    lru.put(91, 91 + "**");// 将91-91用91**替代，并且移动到最前方
    lru.remove(93);// 将93移走
    System.out.println();
    System.out.println("更改后缓存的结果为：");
    lru.sop();
  }
}
