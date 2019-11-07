package demo4;

public class App implements Runnable {
    public static void main(String[] args) {
        Thread t = new Thread(new App());
        t.start();
    }

    @Override
    public void run() {
        for (int i = 1; i < 11; i++) {
            System.out.println("First " + i);
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
