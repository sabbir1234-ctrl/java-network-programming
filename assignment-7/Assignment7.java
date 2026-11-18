import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* ---------------- Unsynchronized Wallet ---------------- */
class Wallet {
    protected float balance;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void addMoney(float amount) {
        float current = balance;          // read
        Thread.yield();                   // widen the race window
        balance = current + amount;       // write (not atomic)
    }

    /** Withdraws the amount only if amount <= balance. Returns true on success. */
    public boolean spendMoney(float amount) {
        if (amount <= balance) {          // check
            float current = balance;      // read
            Thread.yield();
            balance = current - amount;   // write (not atomic)
            return true;
        }
        return false;
    }
}

/* ---------------- Synchronized Wallet ---------------- */
class SynchronizedWallet extends Wallet {
    public SynchronizedWallet() {
        super();
    }

    public SynchronizedWallet(float balance) {
        super(balance);
    }

    @Override
    public synchronized float getBalance() {
        return super.getBalance();
    }

    @Override
    public synchronized void addMoney(float amount) {
        super.addMoney(amount);
    }

    @Override
    public synchronized boolean spendMoney(float amount) {
        return super.spendMoney(amount);
    }
}

/* ---------------- Shared transaction logic ---------------- */
class Transactions {
    static final int ITERATIONS = 100;       // transactions per thread
    static final int SLEEP_MS = 8;

    // Totals of what really happened, used to verify the final balance
    static final AtomicLong totalAdded = new AtomicLong();
    static final AtomicLong totalSpent = new AtomicLong();

    static void reset() {
        totalAdded.set(0);
        totalSpent.set(0);
    }

    static void run(Wallet wallet) {
        Random random = new Random();
        for (int i = 0; i < ITERATIONS; i++) {
            int choice = random.nextInt(13);       // 0..12
            int amount = random.nextInt(5001);     // 0..5000

            if (choice % 2 == 0) {                 // even -> add
                wallet.addMoney(amount);
                totalAdded.addAndGet(amount);
            } else {                               // odd -> withdraw if possible
                if (wallet.spendMoney(amount)) {
                    totalSpent.addAndGet(amount);
                }
            }

            try {
                Thread.sleep(SLEEP_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

/* ---------------- Runnable task ---------------- */
class TransactionTask implements Runnable {
    private final Wallet wallet;

    TransactionTask(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public void run() {
        Transactions.run(wallet);
    }
}

/* ---------------- Thread subclass ---------------- */
class TransactionThread extends Thread {
    private final Wallet wallet;

    TransactionThread(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public void run() {
        Transactions.run(wallet);
    }
}

/* ---------------- Main ---------------- */
public class Assignment7 {
    static final int THREADS = 5;
    static final float INITIAL = 10000f;

    // a) + b1) subclassing Thread
    static void withThreadSubclass(Wallet wallet) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            Thread t = new TransactionThread(wallet);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) t.join();
    }

    // b2) implementing Runnable
    static void withRunnable(Wallet wallet) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            Thread t = new Thread(new TransactionTask(wallet));
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) t.join();
    }

    // b3) Executor framework
    static void withExecutor(Wallet wallet) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) {
            executor.execute(new TransactionTask(wallet));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    interface Approach {
        void run(Wallet wallet) throws InterruptedException;
    }

    static void test(String approachName, Approach approach, boolean sync) throws InterruptedException {
        Wallet wallet = sync ? new SynchronizedWallet(INITIAL) : new Wallet(INITIAL);
        Transactions.reset();

        approach.run(wallet);

        float expected = INITIAL + Transactions.totalAdded.get() - Transactions.totalSpent.get();
        float actual = wallet.getBalance();

        System.out.printf("%-26s %-15s expected = %10.1f  actual = %10.1f  %s%n",
                approachName,
                sync ? "[synchronized]" : "[unsynchronized]",
                expected, actual,
                expected == actual ? "OK" : "<-- RACE CONDITION (lost update)");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initial balance: " + INITIAL + ", threads: " + THREADS
                + ", transactions per thread: " + Transactions.ITERATIONS + "\n");

        for (boolean sync : new boolean[]{false, true}) {
            test("Thread subclass", Assignment7::withThreadSubclass, sync);
            test("Runnable", Assignment7::withRunnable, sync);
            test("Executor framework", Assignment7::withExecutor, sync);
            System.out.println();
        }
    }
}
