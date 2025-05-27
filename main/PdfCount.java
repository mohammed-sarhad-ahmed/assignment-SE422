import java.io.File;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PdfCount {
    public static final ReentrantLock lock = new ReentrantLock(true);
    // ChatGPT help us write the next two methods.
    // We refactored this code to make it simpler from the first assignment with the help of chatgpt
    public static void counter(File folder, Offset offset, String type, CountedValues countedValues) {
        File[] files;
        files = folder.listFiles();
        if (files == null) return;

        int start = offset != null ? offset.getStart() : 0;
        int end = offset != null ? Math.min(offset.getEnd(), files.length - 1) : files.length - 1;

        for (int i = start; i <= end; i++) {
            processFile(files[i], type, countedValues);
        }
    }

    private static void processFile(File file, String type, CountedValues countedValues) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    processFile(child, type, countedValues);
                }
            }
        } else if (file.getName().toLowerCase().endsWith(".pdf")) {
            lock.lock();
            try {
                incrementByType(type, countedValues);
            } finally {
                lock.unlock();
            }
        }
    }

    private static void incrementByType(String type, CountedValues countedValues) {
            switch (type.toLowerCase()) {
                case "single thread":
                    countedValues.incrementSingleThreadCount();
                    break;
                case "four thread":
                    countedValues.incrementFourThreadCount();
                    break;
                case "thread pool":
                    countedValues.incrementPoolThreadCount();

                    synchronized(Resources.tracker) {
                        Resources.poolTaskReady = true;
                        Resources.tracker.notifyAll();
                        try {
                            while (Resources.poolTaskReady) {
                                Resources.tracker.wait();
                            }
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    break;
            }
    }
}


