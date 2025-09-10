import java.io.*;

class Counter {
    private int count = 0;

    // synchronized method prevents race condition
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class Worker extends Thread {
    private Counter counter;

    public Worker(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
}

public class Synchronize {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter number of threads: ");
        int n = Integer.parseInt(br.readLine());

        Counter counter = new Counter();
        Worker[] workers = new Worker[n];

        // create and start threads
        for (int i = 0; i < n; i++) {
            workers[i] = new Worker(counter);
            workers[i].start();
        }

        // wait for all threads to finish
        for (int i = 0; i < n; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("Final Counter Value = " + counter.getCount());
    }
}
