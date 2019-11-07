class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running producer thread");
            this.wait();
            System.out.println("Resumed");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Running consumer thread");
            this.notify();
            Thread.sleep(5000);
            System.out.println("Consumer done");
        }
    }
}

public class App {

    public static void main(String[] args) throws Exception {

        Processor p = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                p.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                p.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
