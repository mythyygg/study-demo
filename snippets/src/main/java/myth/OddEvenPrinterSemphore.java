package myth;
import java.util.concurrent.Semaphore;
public class OddEvenPrinterSemphore {
  private Semaphore semEven = new Semaphore(1);
  private Semaphore semOdd = new Semaphore(0);
  private int MAX = 10;

  public static void main(String[] args) {
    OddEvenPrinterSemphore printer = new OddEvenPrinterSemphore();
    // new Thread(() -> printer.printOdd(), "odd").start();
    // new Thread(() -> printer.printEven(), "even").start();

    new Thread(() -> printer.print(0, "even")).start();
    new Thread(() -> printer.print(1, "odd")).start();
  }

  void print(int mode, String name) {
    for (int i = 0; i < MAX; i++) {
      try {
        if ( i % 2 == mode && mode == 0)  {
          semEven.acquire();
          semOdd.release();
        }
        if ( i % 2 == mode && mode == 1)  {
          semOdd.acquire();
          semEven.release();
        }
        System.out.println(Thread.currentThread().getName() + ":" + i);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  void printEven() {
    for (int i = 0; i < MAX; i = i + 2) {
      try {
        semEven.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.println(Thread.currentThread().getName() + ":" + i);
      semOdd.release();
    }
  }

  void printOdd() {
    for (int i = 1; i < MAX; i = i + 2) {
      try {
        semOdd.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.println(Thread.currentThread().getName() + ":" + i);
      semEven.release();
    }
  }
}
