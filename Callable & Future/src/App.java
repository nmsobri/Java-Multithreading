import java.util.Random;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) {


        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        Future future = threadPool.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);

            if (duration > 2000) {
                throw new Exception("Sleep too long");
            }

            System.out.println("Hello world");
            return duration;
        });

        threadPool.shutdown();


        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Ended");
    }
}
