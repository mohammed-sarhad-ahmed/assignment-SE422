import java.io.File;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class PdfThread implements Runnable {
    private final File folder;
    private final String type;
    private final int numOfThreads;
    private final int currentThreadNum;
    private final CountedValues countedValues;
    private CountDownLatch latch;

    public PdfThread(File folder, String type, CountedValues countedValues ){
        this.folder=folder;
        this.type=type;
        this.numOfThreads=1;
        this.currentThreadNum=1;
        this.countedValues=countedValues;
    }
    public PdfThread(File folder, int numOfThread, int currentThreadNum, String type, CountedValues countedValues){
        this.folder=folder;
        this.currentThreadNum=currentThreadNum;
        this.numOfThreads=numOfThread;
        this.type=type;
        this.countedValues=countedValues;
    }
    public PdfThread(File folder, int numOfThread, int currentThreadNum, String type, CountedValues countedValues,CountDownLatch latch){
        this.folder=folder;
        this.currentThreadNum=currentThreadNum;
        this.numOfThreads=numOfThread;
        this.type=type;
        this.countedValues=countedValues;
        this.latch=latch;
    }
    @Override
    public void run(){
         int numberOfFiles=folder.listFiles().length;
         Offset offset= getStartEnd(numberOfFiles,numOfThreads,currentThreadNum);
         PdfCount.counter(folder,offset,type,countedValues);
         if(type.equals("thread pool")){
             latch.countDown();
         }

        }
    // ChatGPT helped with the logic for this part.
    // we had the idea of having an object that has the start and end position for each thread to
    // We asked, "We have a list of files, and we want to divide it as evenly as possible among multiple threads. Each thread must know its starting and ending positions. How can we calculate the start and end positions?"
    // It suggested multiple approaches, and we adapted the logic to fit our needs by returning an Offset object

    public static Offset getStartEnd(int totalFiles, int numOfThreads, int currentThreadNum) {
        double chunkSize = (double) totalFiles / numOfThreads;
        int start = (int) Math.floor(chunkSize * (currentThreadNum - 1));
        int end = (int) Math.floor(chunkSize * currentThreadNum) - 1;
        end = Math.min(end, totalFiles - 1);
        return new Offset(start, end);
    }

}

