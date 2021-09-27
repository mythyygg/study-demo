package myth;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsummer {
    private int maxSize = 10;
    private ArrayBlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(maxSize);

    public static void main(String[] args) {
        ProducerConsummer t = new ProducerConsummer();
        new Thread(() -> t.produce()).start();
        new Thread(() -> t.consummer()).start();
    }

    public void produce() {
            while (true) {
                try {
                    int x = new Random().nextInt(100);
                    Thread.sleep(new Random().nextInt(10));
                    bq.put(x);
                    System.out.println("put:"+x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    public void consummer(){
            while (true) {
                try {
                    int x = bq.take();
                    Thread.sleep(new Random().nextInt(10));
                    System.out.println("take:"+x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
