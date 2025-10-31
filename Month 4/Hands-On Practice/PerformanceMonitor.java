import java.io.*;

public class PerformanceMonitor {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter number of iterations to test performance: ");
        int n = Integer.parseInt(br.readLine());

        long start = System.currentTimeMillis();
        long sum = 0;
        for(int i=0;i<n;i++) sum += Math.pow(i,2);
        long end = System.currentTimeMillis();

        System.out.println("Execution Time: "+(end-start)+" ms");
        System.out.println("CPU Utilization Estimate: "+(sum%100)+"%");
    }
}
