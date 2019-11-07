package demo5;

public class App {
    public static void main(String[] args) {
        new Thread(() -> {
            for (var i = 0; i < 11; i++) {
                System.out.println("hello " + i);

                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("Running from main thread");

    }
}
