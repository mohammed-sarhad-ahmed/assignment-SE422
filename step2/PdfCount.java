import java.io.File;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PdfCount {
    // ChatGPT help us write the next two methods.
    // We refactored this code to make it simpler from the first assignment with the help of chatgpt
    private static final ReentrantLock lock = new ReentrantLock(true);
    public static void counter(File folder, Offset offset, String type, CountedValues countedValues) {
        File[] files;
        lock.lock();
        try {
            files = folder.listFiles();
        } finally {
            lock.unlock();
        }
        if (files == null) return;

        int start = offset != null ? offset.getStart() : 0;
        int end = offset != null ? Math.min(offset.getEnd(), files.length - 1) : files.length - 1;

        for (int i = start; i <= end; i++) {
            processFile(files[i], type, countedValues);
        }
    }

    private static void processFile(File file, String type, CountedValues countedValues) {
        File[] children = null;

        lock.lock();
        try {
            if (file.isDirectory()) {
                children = file.listFiles();
            } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                incrementByType(type, countedValues);
            }
        } finally {
            lock.unlock();
        }
        if (children != null) {
            for (File f : children) {
                processFile(f, type, countedValues);
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
                    try {
                        Resources.lock.lock();
                        countedValues.incrementPoolThreadCount();
                        synchronized(Resources.tracker) {
                            Resources.tracker.notify();
                            try {
                                Resources.tracker.wait();
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }finally {
                        Resources.lock.unlock();
                    }
                    break;
            }
    }
}


