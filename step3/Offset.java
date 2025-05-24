import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Offset {
    private final int start;
    private final int end;
    private final ReentrantReadWriteLock reentrantReadWriteLockStart;
    private final ReentrantReadWriteLock reentrantReadWriteLockEnd;

    public Offset(int start,int end){
        this.start=start;
        this.end=end;
        reentrantReadWriteLockStart=new ReentrantReadWriteLock();
        reentrantReadWriteLockEnd=new ReentrantReadWriteLock();
    }

    public int getEnd() {
        try{
            reentrantReadWriteLockEnd.readLock().lock();
            return end;
        }finally {
            reentrantReadWriteLockEnd.readLock().unlock();
        }
    }

    public int getStart() {
        try{
            reentrantReadWriteLockStart.readLock().lock();
            return start;
        }finally {
            reentrantReadWriteLockStart.readLock().unlock();
        }    }
}
