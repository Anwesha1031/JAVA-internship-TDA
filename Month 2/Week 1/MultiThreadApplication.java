import java.io.*;

class NumberPrinter extends Thread {
    private int start, end;

    public NumberPrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println(Thread.currentThread().getName() + " prints: " + i);
            try {
                Thread.sleep(200); // slow down for visibility
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class MultiThreadApplication {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter start number: ");
        int start = Integer.parseInt(br.readLine());

        System.out.print("Enter end number: ");
        int end = Integer.parseInt(br.readLine());

        // Create two threads dividing the range
        int mid = (start + end) / 2;

        NumberPrinter t1 = new NumberPrinter(start, mid);
        NumberPrinter t2 = new NumberPrinter(mid + 1, end);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}
