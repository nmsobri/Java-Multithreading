import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void stageOne() {
        synchronized (this.lock1) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.list1.add(this.random.nextInt(100));
        }
    }

    public void stageTwo() {
        synchronized (this.lock2) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.list2.add(this.random.nextInt(100));
        }
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            this.stageOne();
            this.stageTwo();
        }
    }

    public void main() {
        System.out.println("Starting...");
        long start = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            this.process();
        });

        Thread t2 = new Thread(() -> {
            this.process();
        });

        t.start();
        t2.start();

        try {
            t.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
        System.out.println("List1: " + this.list1.size() + ", List2: " + this.list2.size());
    }
}
