import java.util.concurrent.SynchronousQueue;

public class PrinterThread extends Thread {
    private final SynchronousQueue<String> synchronousQueue;
    private final CountedValues countedValues;

    public PrinterThread(SynchronousQueue<String> synchronousQueue, CountedValues countedValues) {
        this.synchronousQueue = synchronousQueue;
        this.countedValues = countedValues;
    }

    @Override
    public void run() {
        try {
            while (Event.getActiveThread().equals("Single") ||Event.getActiveThread().equals("Four Thread") ) {
                try {
                    String value = synchronousQueue.take();
                    if (value.equals("done")) break;
                    System.out.println(value);
                } catch (InterruptedException ex) {
                    System.out.println("something went wrong");
                }
            }
            while (!Event.getIsFinished()){
                synchronized (Resources.tracker) {
                    Resources.tracker.wait();
                    if(Event.getActiveThread().equals("done")) break;
                    System.out.println("The current value for the thread pool is "+ countedValues.getPoolThreadCount());
                    Resources.tracker.notifyAll();
                }
            }
            System.out.println("finishing up...");
            System.out.println("the final value for the single thread counter is "+countedValues.getSingleThreadCount());
            System.out.println("the final value for the four thread counter is "+countedValues.getFourThreadCount());
            System.out.println("the final value for the thread pool counter is "+countedValues.getPoolThreadCount());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
