import java.util.concurrent.SynchronousQueue;

public class PrinterThread extends Thread {
    private final CountedValues countedValues;

    public PrinterThread(CountedValues countedValues) {
        this.countedValues = countedValues;
    }

    @Override
    public void run() {
        try {
            System.out.println("final count...");
            System.out.println("the final value for the single thread counter is "+countedValues.getSingleThreadCount());
            System.out.println("the final value for the four thread counter is "+SharedCounter.getFourThreadCountedValue());
            System.out.println("the final value for the thread pool counter is "+countedValues.getPoolThreadCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
