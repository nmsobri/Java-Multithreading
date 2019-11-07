import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = null;
    private int connections = 0;
    private Semaphore semaphore = new Semaphore(10);

    private Connection() {
    }

    public static Connection getInstance() {
        if (Connection.instance == null) {
            Connection.instance = new Connection();
        }

        return Connection.instance;
    }

    public void connect() {
        try {
            this.semaphore.acquire();
            this.doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.semaphore.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
            System.out.println("Connection is released , connection count: " + connections);
        }
    }

}
