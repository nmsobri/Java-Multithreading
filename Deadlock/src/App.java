import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
    private Account firstAccount = new Account();
    private Account secondAccount = new Account();

    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    public void acquireLock(ReentrantLock lock1, ReentrantLock lock2) {
        boolean gotLock1 = false;
        boolean gotLock2 = false;

        while (true) {
            gotLock1 = lock1.tryLock();
            gotLock2 = lock2.tryLock();

            if (gotLock1 && gotLock2) {
                return;
            }

            if (gotLock1) {
                lock1.unlock();
            }

            if (gotLock2) {
                lock2.unlock();
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void firstThread() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            this.acquireLock(this.lock1, this.lock2);

            try {
                Account.transfer(this.firstAccount, this.secondAccount, random.nextInt(100));
            } finally {
                this.lock1.unlock();
                this.lock2.unlock();
            }

        }
    }

    public void secondThread() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            this.acquireLock(this.lock2, this.lock1);

            try {
                Account.transfer(this.secondAccount, this.firstAccount, random.nextInt(100));
            } finally {
                this.lock1.unlock();
                this.lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + this.firstAccount.getBalance());
        System.out.println("Account 2 balance: " + this.secondAccount.getBalance());
        System.out.println("Total balance: " + (this.firstAccount.getBalance() + this.secondAccount.getBalance()));
    }
}

public class App {
    public static void main(String[] args) {
        Runner runner = new Runner();

        Thread t1 = new Thread(() -> {
            runner.firstThread();
        });

        Thread t2 = new Thread(() -> {
            runner.secondThread();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        runner.finished();
    }
}
