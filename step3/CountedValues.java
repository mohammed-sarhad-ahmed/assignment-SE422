import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class CountedValues {
    private volatile LongAdder singleThreadCount;
    private volatile LongAdder fourThreadCount;
    private volatile LongAdder poolThreadCount;
    private final ReentrantReadWriteLock singleLock;
    private final ReentrantReadWriteLock fourThreadLock;
    private final ReentrantReadWriteLock poolThreadLock;
    private final SynchronousQueue<String> syncQ;


    public CountedValues(SynchronousQueue<String> syncQ) {
        singleLock = new ReentrantReadWriteLock();
        fourThreadLock = new ReentrantReadWriteLock();
        poolThreadLock = new ReentrantReadWriteLock();
        poolThreadCount=new LongAdder();
        singleThreadCount=new LongAdder();
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

    public Long getPoolThreadCount() {
        try{
            poolThreadLock.readLock().lock();
            return  poolThreadCount.sum();
        }
        finally {
            poolThreadLock.readLock().unlock();

        }
    }

    public Long getSingleThreadCount() {
        try {
            singleLock.readLock().lock();
            return singleThreadCount.sum();
        }finally {
            singleLock.readLock().unlock();
        }
    }

    public void incrementSingleThreadCount() {
       try{
           singleLock.writeLock().lock();
          singleThreadCount.increment();
       }finally {
           try {
               String value="the current value for the single thread is "+getSingleThreadCount();
               syncQ.put(value);
           }catch (Exception e){
               System.out.println(e.getMessage());
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
                String value="the current value for the four threads is "+getFourThreadCount();
                syncQ.put(value);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            fourThreadLock.writeLock().unlock();
        }
    }

    public void incrementPoolThreadCount() {
       try{
           poolThreadLock.writeLock().lock();
           poolThreadCount.increment();
       }finally {
           try {
               String value="the current value for the thread pool is "+getPoolThreadCount();
               syncQ.put(value);
           }catch (Exception e){
               System.out.println(e.getMessage());
           }
           poolThreadLock.writeLock().unlock();

       }
    }
}
