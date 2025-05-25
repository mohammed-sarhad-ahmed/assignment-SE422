public class PrinterThread extends Thread {
    private final CountedValues countedValues;

    public PrinterThread (CountedValues countedValues){
        this.countedValues=countedValues;
    }

    @Override
    public void run() {
        try {
            while (!Event.getIsFinished()){
                synchronized (Resources.tracker) {
                    Resources.tracker.wait();
                     if (Event.getActiveThread().equals("Single")){
                         System.out.println("The current value for the single thread is "+ countedValues.getSingleThreadCount());
                     }else if (Event.getActiveThread().equals("Four Thread")){
                         System.out.println("The current value for the four thread is "+ countedValues.getFourThreadCount());
                     } else if (Event.getActiveThread().equals("Thread Pool")) {
                         System.out.println("The current value for the thread pool is "+ countedValues.getPoolThreadCount());
                     }
                     Resources.tracker.notifyAll();
                }
            }
            System.out.println("finish up");
            System.out.println("the final value for the single thread counter is "+countedValues.getSingleThreadCount());
            System.out.println("the final value for the four thread counter is "+countedValues.getFourThreadCount());
            System.out.println("the final value for the thread pool counter is "+countedValues.getPoolThreadCount());
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
