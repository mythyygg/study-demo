package myth;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Queue;
import java.util.LinkedList;

public class BoundedBlockingQueue<E> {
    private int capacity;
    private Queue<E> queue;
    private Lock lock = new ReentrantLock();
    private Condition notFull = this.lock.newCondition();
    private Condition notEmpty = this.lock.newCondition();

    public BoundedBlockingQueue(int capacity) throws Exception {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void push(E obj) throws Exception {
        this.lock.lock();
        try {
            while (this.capacity == this.queue.size())
                this.notFull.wait();
            this.queue.add(obj);
            this.notEmpty.notifyAll();
        } finally {
            this.lock.unlock();
        }
    }

    public E pop() throws Exception {
        this.lock.lock();
        try {
            while (this.queue.size() == 0)
                this.notEmpty.wait();
            E result = this.queue.poll();
            notFull.notifyAll();
            return result;
        } finally {
            this.lock.unlock();
        }
    }
}
