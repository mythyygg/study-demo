package myth;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket {
    private Lock lock = new ReentrantLock();
    private AtomicInteger ticket;

    public Ticket(int ticketSema) {
        this.ticket = new AtomicInteger(ticketSema);
    }

    public void getTicket(){
        while(true) {
            lock.lock();
            try {
                if (ticket.get() < 0) {
                    System.out.println("ticket empty");
                    break;
                } else {
                    System.out.println("ticket success:" + ticket.decrementAndGet());
                    Thread.sleep(new Random().nextInt(100));
                }
            } catch (InterruptedException e) {

            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Ticket t = new Ticket(100);
        for (int i = 0; i < 3; i++ ){
            new Thread(t::getTicket).start();
        }
    }
}
