import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Running: " + this.id);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Completed: " + this.id);
    }
}

public class App {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        threadPool.submit(new Processor(1));
        threadPool.submit(new Processor(2));
        threadPool.submit(new Processor(3));
        threadPool.submit(new Processor(4));
        threadPool.submit(new Processor(5));

        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Fetching data from remote api...");

                try {
                    Thread.sleep(15000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Finished fetching data from remote api");
            }
        });

        threadPool.submit(new Thread(() -> {
            System.out.println("Downloading some image from internet..");

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Downloading image finished");
        }));

        threadPool.shutdown();

        System.out.println("All task has been submitted..Running the processing....");

        try {
            threadPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
