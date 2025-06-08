import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Event {
    private static volatile String activeThread="Single";
    private static final ReentrantReadWriteLock reentrantReadWriteLockActiveThread=new ReentrantReadWriteLock();
    public static void setActiveThread(String currentThread){
        try {
            reentrantReadWriteLockActiveThread.writeLock().lock();
            activeThread=currentThread;
        }finally {
            reentrantReadWriteLockActiveThread.writeLock().unlock();
        }
    }
    public static String getActiveThread(){
        try {
            reentrantReadWriteLockActiveThread.readLock().lock();
            return activeThread;
        }finally {
            reentrantReadWriteLockActiveThread.readLock().unlock();
        }
    }

}
