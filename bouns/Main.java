//Shania mohamad hamasaid
//Sm21114@auis.edu.krd
//Mohammed Sarhad Ahmed
//Ms21126@auis.edu.krd
//We put the references where we used them.



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

         boolean doesTheFolderExist = false;
         do {
             try {
                 //We asked chatgpt "how to get an existing directory from a user in java"
                 System.out.println("Please enter an existing directory:");
                 String folderPath = sc.nextLine();
                 File folder = new File(folderPath);

                 if (folder.isDirectory() && folder.exists()) {
                     doesTheFolderExist = true;
                     CountedValues countedValues = new CountedValues();
                     Thread t1 = new Thread(new PdfThread(folder, "single thread", countedValues));
                     t1.start();
                     t1.join();
                     Thread[] threads = new Thread[4];
                     for (int i = 0; i < 4; i++) {
                         threads[i] = new Thread(new PdfThread(folder, 4, i + 1, "four thread"));
                         threads[i].start();
                     }
                     for (Thread thread : threads) {
                         thread.join();
                     }
                     int halfOfTheCoresCount = Runtime.getRuntime().availableProcessors() / 2;
                     //we already had this for waiting for all thread pools to finish we renamed it to waitForFinish
                     // to avoid confusion with the new requirement of step 3
                     CountDownLatch waitForFinish = new CountDownLatch(halfOfTheCoresCount);
                     try (ExecutorService pool = Executors.newFixedThreadPool(halfOfTheCoresCount)) {
                         for (int i = 0; i < halfOfTheCoresCount; i++) {
                             int finalI = i;
                             pool.execute(new PdfThread(folder,halfOfTheCoresCount, finalI +1,"thread pool",countedValues,waitForFinish));
                         }
                         waitForFinish.await();
                         new PrinterThread(countedValues).start();
                     }
                 } else {
                     throw new FileNotFoundException();
                 }
             } catch (FileNotFoundException e) {
                 System.out.println("The path was not correct. Please try again.");
             } catch (InterruptedException e) {
                 System.out.println("A thread was interrupted.");
             }
         } while (!doesTheFolderExist);
      sc.close();
    }
}
