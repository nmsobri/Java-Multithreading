package demo1;

import java.util.Scanner;

class Processor extends Thread {
    private volatile boolean running = true;

    @Override
    public void run() {
        int i = 1;
        while (this.running) {
            System.out.println("Hello " + i);
            i++;

            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}

public class App {
    public static void main(String[] args) {
        Processor p = new Processor();
        p.start();

        System.out.println("Press enter to stop");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        p.shutdown();
    }
}
