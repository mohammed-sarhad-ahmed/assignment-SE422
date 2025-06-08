import java.io.File;

public class PdfFourThreadCounter {
    public static void counter(File folder, Offset offset) {
        File[] files;
        files = folder.listFiles();
        if (files == null) return;

        int start = offset != null ? offset.getStart() : 0;
        int end = offset != null ? Math.min(offset.getEnd(), files.length - 1) : files.length - 1;

        for (int i = start; i <= end; i++) {
            processFile(files[i]);
        }
    }

    private static void processFile(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    processFile(child) ;
                }
            }
        } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                increment();
        }
    }

    private static void increment()  {
        SharedCounter.setFourThreadCountedValue();
    }
}


