import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Processor {
    private int value = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = this.lock.newCondition();

    public void increment() {
        for (int i = 0; i < 10000; i++) {
            this.value++;
        }
    }

    public void firstThread() {
        this.lock.lock();
        System.out.println("Waiting...");

        try {
            this.condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Woken up");

        try {
            this.increment();
        } finally {
            this.lock.unlock();
        }
    }

    public void secondThread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.lock.lock();
        System.out.println("Press return key");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key");
        this.condition.signal();

        System.out.println("REsumed secondThread");

        try {
            System.out.println("INcrement in secondThread");
            this.increment();
        } finally {
            this.lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Value is: " + this.value);
    }
}

public class App {

    public static void main(String[] args) {
        Processor p = new Processor();

        Thread t1 = new Thread(() -> {
            p.firstThread();
        });

        Thread t2 = new Thread(() -> {
            p.secondThread();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        p.finished();
    }
}
