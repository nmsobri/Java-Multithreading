import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class Producer {
    private Object lock;
    private LinkedList<Integer> list;

    public Producer() {
        this.lock = new Object();
        this.list = new LinkedList<>();
    }

    public void producer() {
        int val = 0;

        while (true) {
            synchronized (this.lock) {

                while (this.list.size() == 20) {
                    try {
                        this.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.list.add(val++);
                System.out.println("Producer - List size: " + this.list.size());
                this.lock.notify();
            }
        }
    }

    public void consumer() {
        while (true) {
            synchronized (this.lock) {

                if (this.list.size() == 0) {
                    try {
                        this.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                this.list.removeFirst();
                System.out.println("Consumer - List size: " + this.list.size());
                this.lock.notify();
            }

            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Producer p = new Producer();

        Thread t1 = new Thread(() -> {
            p.producer();
        });

        Thread t2 = new Thread(() -> {
            p.consumer();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
