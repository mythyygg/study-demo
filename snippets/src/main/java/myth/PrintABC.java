package myth;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PrintABC implements Runnable {

  private static ReentrantLock lock = new ReentrantLock();
  private static List<String> arr = Arrays.asList("A", "B", "C", "D");
  private static List<Condition> conds = arr.stream().map(i -> lock.newCondition())
      .collect(Collectors.toList());
  private static int token = 0;
  private int repeat = 3;
  private int index;

  private PrintABC(int index) {
    this.index = index;
  }

  public static void main(String[] args) {
    for (int i = 0; i < arr.size(); i++) {
      new Thread(new PrintABC(i), arr.get(i)).start();
    }
  }

  @Override
  public void run() {
    try {
      lock.lock();
      for (int i = 0; i < repeat; i++) {
        while (token != index) {
          conds.get(index).await();
        }
        System.out.print(Thread.currentThread().getName());
        token = (token + 1) % conds.size();
        conds.get(token).signal();
      }
    } catch (InterruptedException ignored) {

    } finally {
      lock.unlock();
    }
  }

}
