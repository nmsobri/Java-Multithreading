import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(200);

        for (int i = 0; i < 200; i++) {
            threadPool.submit(new Thread(() -> {
                Connection.getInstance().connect();
            }));
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
