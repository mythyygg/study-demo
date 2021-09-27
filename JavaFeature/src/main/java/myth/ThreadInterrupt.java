package myth;


public class ThreadInterrupt extends Thread {

    public volatile boolean exit = false;

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("kdfj");// 延迟50秒
        }
    }

    public void stopT() {
        exit = true;
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new ThreadInterrupt();
        thread.start();
        thread.join();
        Thread.sleep(1000);
        thread.start();

        System.out.println("线程已经退出!");
    }

}