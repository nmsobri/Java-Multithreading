import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Processing...");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.latch.countDown();
    }
}

public class App {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);

        threadPool.submit(new Processor(latch));
        threadPool.submit(new Processor(latch));
        threadPool.submit(new Processor(latch));

        threadPool.shutdown();

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All completed");
    }
}
