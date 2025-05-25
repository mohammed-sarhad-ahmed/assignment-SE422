import java.io.File;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PdfCount {
    // ChatGPT help us write the next two methods.
    // The problem was that we didn’t know how to handle the case of a folder containing a subfolder with PDF files.
    // we asked "How can we count the number of PDF files inside a folder.the folder might have subfolders with PDF files"
    // The code ChatGPT provided needed some changes, which we made ourselves to adapt it to work with the offset object.
    public static void counter(File folder, Offset offset, String type, CountedValues countedValues) {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (int i = offset.getStart(); i <= offset.getEnd() && i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                counter(file, type, countedValues);
            } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                incrementByType(type, countedValues);
            }
        }
    }
    private static void counter(File folder, String type, CountedValues countedValues) {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                counter(file, type, countedValues); // recurse again
            } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                incrementByType(type, countedValues);
            }
        }
    }


    private static void incrementByType(String type, CountedValues countedValues) {
       try{
           Resources.lock.lock();
           switch (type.toLowerCase()) {
               case "single thread":
                   countedValues.incrementSingleThreadCount();
                   break;
               case "four thread":
                   countedValues.incrementFourThreadCount();
                   break;
               case "thread pool":
                   countedValues.incrementPoolThreadCount();
                   break;

           }
           synchronized(Resources.tracker) {
               Resources.tracker.notify();
               try {
                   Resources.tracker.wait();
               }catch (Exception e){
                   System.out.println(e.getMessage());

               }
           }
       }finally{
           Resources.lock.unlock();
       }
            }
        }


