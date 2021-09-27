package myth.AQS;

import java.util.concurrent.TimeUnit;

public class ReentrantLock implements Lock, java.io.Serializable {

  private static final long serialVersionUID = 7373984872572414699L;

  private final Sync sync;

  abstract static class Sync extends AbstractQueuedSynchronizer {

    private static final long serialVersionUID = -5179523762034025860L;

    abstract void lock();

    final boolean nonfairTryAcquire(int acquires) {
      final Thread current = Thread.currentThread();
      int c = getState();
      if (c == 0) {
        if (compareAndSetState(0, acquires)) {
          setExclusiveOwnerThread(current);
          return true;
        }
      } else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
        {
          throw new Error("Maximum lock count exceeded");
        }
        setState(nextc);
        return true;
      }
      return false;
    }

    protected final boolean tryRelease(int releases) {
      int c = getState() - releases;
      if (Thread.currentThread() != getExclusiveOwnerThread()) {
        throw new IllegalMonitorStateException();
      }
      boolean free = false;
      if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
      }
      setState(c);
      return free;
    }

    protected final boolean isHeldExclusively() {
      // While we must in general read state before owner,
      // we don't need to do so to check if current thread is owner
      return getExclusiveOwnerThread() == Thread.currentThread();
    }

    final ConditionObject newCondition() {
      return new ConditionObject();
    }

    // Methods relayed from outer class

    final Thread getOwner() {
      return getState() == 0 ? null : getExclusiveOwnerThread();
    }

    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
      s.defaultReadObject();
      setState(0); // reset to unlocked state
    }
  }

  static final class NonfairSync extends Sync {

    private static final long serialVersionUID = 7316153563782823691L;

    final void lock() {
      if (compareAndSetState(0, 1)) {
        setExclusiveOwnerThread(Thread.currentThread());
      } else {
        acquire(1);
      }
    }

    protected final boolean tryAcquire(int acquires) {
      return nonfairTryAcquire(acquires);
    }
  }

  static final class FairSync extends Sync {

    private static final long serialVersionUID = -3000897897090466540L;

    final void lock() {
      acquire(1);
    }

    //没有try
    protected final boolean tryAcquire(int acquires) {
      final Thread current = Thread.currentThread();
      int c = getState();
      if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
          setExclusiveOwnerThread(current);
          return true;
        }
      } else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) {
          throw new Error("Maximum lock count exceeded");
        }
        setState(nextc);
        return true;
      }
      return false;
    }
  }

  public ReentrantLock() {
    sync = new NonfairSync();
  }

  public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
  }

  public void lock() {
    sync.lock();
  }

  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  public boolean tryLock() {
    return sync.nonfairTryAcquire(1);
  }

  public boolean tryLock(long timeout, TimeUnit unit)
      throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(timeout));
  }

  public void unlock() {
    sync.release(1);
  }

  public Condition newCondition() {
    return sync.newCondition();
  }

  public String toString() {
    Thread o = sync.getOwner();
    return super.toString() + ((o == null) ?
        "[Unlocked]" :
        "[Locked by thread " + o.getName() + "]");
  }
}
