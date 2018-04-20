package utils;

/**
 * Created by SChubuk on 20.04.2018.
 */
public class Waiter {
    //Thread.sleep used where pause is necessary and could not be skipped by
    //setting run.slow to false

    public static void wait(int timeMilliseconds)  {
        Boolean slow = Boolean.getBoolean("run.slow");
        if(slow){
            try {
                Thread.sleep(timeMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
