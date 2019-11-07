package demo1;

public class App {
    private int count = 0;

    public static void main(String[] args) {
        App app = new App();
        app.work();
    }

    public synchronized void increment() {
        this.count++;
    }

    public void work() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                this.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                this.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("The value of count is: " + this.count);
    }
}
