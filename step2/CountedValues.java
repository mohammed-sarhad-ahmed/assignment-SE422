import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class CountedValues {

    //we only change the counter for the counter related to 4 threads
    private int singleThreadCount;
    private final LongAdder fourThreadCount;
    private volatile int poolThreadCount;
    private final ReentrantReadWriteLock singleLock;
    private final ReentrantReadWriteLock fourThreadLock;
    private final ReentrantReadWriteLock poolThreadLock;
    private final SynchronousQueue<String> syncQ;


    public CountedValues(SynchronousQueue<String> syncQ) {
        singleLock = new ReentrantReadWriteLock();
        fourThreadLock = new ReentrantReadWriteLock();
        poolThreadLock = new ReentrantReadWriteLock();
        fourThreadCount=new LongAdder();
        this.syncQ=syncQ;
    }

    public Long getFourThreadCount() {
        try {
            fourThreadLock.readLock().lock();
            return fourThreadCount.sum();
        }finally {
            fourThreadLock.readLock().unlock();
        }
    }

    public int getPoolThreadCount() {
        try{
            poolThreadLock.readLock().lock();
            return  poolThreadCount;
        }
        finally {
            poolThreadLock.readLock().unlock();

        }
    }

    public int getSingleThreadCount() {
        try {
            singleLock.readLock().lock();
            return singleThreadCount;
        }finally {
            singleLock.readLock().unlock();
        }
    }

    public void incrementSingleThreadCount()  {
       try{
           singleLock.writeLock().lock();
          singleThreadCount++;
       }finally {
           try {
               //we asked chatgpt "how to change int to string in java"
               syncQ.put(Integer.toString(getSingleThreadCount()));
           } catch (InterruptedException e) {
               System.out.println(e);
           }
           singleLock.writeLock().unlock();
       }
    }

    public void incrementFourThreadCount() {
        try{
            fourThreadLock.writeLock().lock();
            fourThreadCount.increment();
        }finally {
            try {
                //we asked chatgpt "how to change long to string in java"
                syncQ.put(Long.toString(getFourThreadCount()));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            fourThreadLock.writeLock().unlock();
        }
    }

    public void incrementPoolThreadCount() {
       try{
           poolThreadLock.writeLock().lock();
           poolThreadCount++;
       }finally {
           poolThreadLock.writeLock().unlock();

       }
    }
}
