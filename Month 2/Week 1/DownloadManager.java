import java.io.*;

// Thread class simulating file download
class FileDownloader extends Thread {
    private String fileName;
    private int fileSize;

    public FileDownloader(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public void run() {
        System.out.println("Starting download: " + fileName);

        for (int i = 1; i <= fileSize; i++) {
            try {
                Thread.sleep(300); // simulate download time
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(fileName + " -> " + (i * 10) + "% downloaded");
        }

        System.out.println("Download complete: " + fileName);
    }
}

public class DownloadManager {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter number of files to download: ");
        int n = Integer.parseInt(br.readLine());

        FileDownloader[] downloads = new FileDownloader[n];

        // Taking file names from user
        for (int i = 0; i < n; i++) {
            System.out.print("Enter file name " + (i + 1) + ": ");
            String fileName = br.readLine();

            // simulate file size in chunks (10 chunks = 100%)
            downloads[i] = new FileDownloader(fileName, 10);
        }

        // Start all downloads simultaneously
        for (int i = 0; i < n; i++) {
            downloads[i].start();
        }

        // Wait until all downloads complete
        for (int i = 0; i < n; i++) {
            try {
                downloads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("\nAll downloads finished successfully!");
    }
}
