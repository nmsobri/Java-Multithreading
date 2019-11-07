import java.util.Random;

public class App {

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            Random r = new Random();
            for (int i = 0; i < 1E8; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("I've been interrupted");
                    break;
                }
                Math.sin(r.nextDouble());
            }
        });
        System.out.println("Starting...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.start();
        t.interrupt();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ended");
    }
}
