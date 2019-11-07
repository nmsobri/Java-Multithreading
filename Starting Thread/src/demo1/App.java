package demo1;

class Runner extends Thread {
    private String what;

    public Runner(String what) {
        this.what = what;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(this.what + " " + i);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Thread t1 = new Runner("First");
        Thread t2 = new Runner("Second");

        t1.start();
        t2.start();
    }
}
