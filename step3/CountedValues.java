import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class CountedValues {
    private volatile int singleThreadCount;
    private volatile LongAdder fourThreadCount;
    private volatile int poolThreadCount;
    private final ReentrantReadWriteLock singleLock;
    private final ReentrantReadWriteLock fourThreadLock;
    private final ReentrantReadWriteLock poolThreadLock;

    public CountedValues() {
        singleLock = new ReentrantReadWriteLock();
        fourThreadLock = new ReentrantReadWriteLock();
        poolThreadLock = new ReentrantReadWriteLock();
        fourThreadCount=new LongAdder();
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

    public void incrementSingleThreadCount() {
       try{
           singleLock.writeLock().lock();
           singleThreadCount++;
       }finally {
           singleLock.writeLock().unlock();
       }
    }

    public void incrementFourThreadCount() {
        try{
            fourThreadLock.writeLock().lock();
            fourThreadCount.increment();
        }finally {
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
