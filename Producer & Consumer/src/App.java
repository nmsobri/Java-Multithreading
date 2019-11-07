import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {
    private static BlockingQueue<Integer> list = new ArrayBlockingQueue<Integer>(10);

    private static void producer() {
        Random rnd = new Random();
        while (true) {
            try {
                App.list.put(rnd.nextInt(100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer() {
        Random rnd = new Random();

        while (true) {
            try {
                Thread.sleep(100);
                if (rnd.nextInt(10) == 0) {
                    Integer val = App.list.take();
                    System.out.println("Taken value: " + val + "; List size is: " + App.list.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            App.producer();
        });

        Thread t2 = new Thread(() -> {
            App.consumer();
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
