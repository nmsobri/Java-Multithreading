public class Account {
    private int balance = 10000;


    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void transfer(Account a1, Account a2, int amount) {
        a1.withdraw(amount);
        a2.deposit(amount);
    }
}
