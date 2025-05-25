import java.util.concurrent.SynchronousQueue;

public class PrinterThread extends Thread {
    private final SynchronousQueue<String> synchronousQueue;
    private String value;


   public PrinterThread( SynchronousQueue<String> synchronousQueue){
    this.synchronousQueue=synchronousQueue;
   }

    @Override
    public void run() {
       try {
           while (!(value = synchronousQueue.take()).equals("finish")){
              System.out.println(value);
           }
           System.out.println("finishing up...");

       }catch (Exception e){
           System.out.println("something went wrong");
       }


    }
}
