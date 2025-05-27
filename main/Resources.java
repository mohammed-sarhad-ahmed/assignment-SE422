import java.util.concurrent.locks.ReentrantLock;

public class Resources {
    public static final Object tracker=new Object();
    public static volatile boolean poolTaskReady = false;
}
