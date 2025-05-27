import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Event {
    private static volatile boolean isFinished;
    private static volatile String activeThread="Single";
    private static final ReentrantReadWriteLock reentrantReadWriteLockIsFinish=new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock reentrantReadWriteLockActiveThread=new ReentrantReadWriteLock();
    public static void changeIsFinished(){
        try {
            reentrantReadWriteLockIsFinish.writeLock().lock();
            isFinished=!isFinished;
        }finally {
            reentrantReadWriteLockIsFinish.writeLock().unlock();
        }
    }

    public static boolean getIsFinished(){
        try {
            reentrantReadWriteLockIsFinish.readLock().lock();
            return isFinished;
        }finally {
            reentrantReadWriteLockIsFinish.readLock().unlock();
        }
    }

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
