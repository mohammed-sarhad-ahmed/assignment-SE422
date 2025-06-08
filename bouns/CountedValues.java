import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class CountedValues {

    //we only change the counter for the counter related to 4 threads
    private int singleThreadCount;
    private int poolThreadCount;
    private final ReentrantReadWriteLock singleLock;
    private final ReentrantReadWriteLock poolThreadLock;


    public CountedValues() {
        singleLock = new ReentrantReadWriteLock();
        poolThreadLock = new ReentrantReadWriteLock();
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
           singleLock.writeLock().unlock();
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
