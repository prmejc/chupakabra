package time;

import models.Task;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 3.3.2013
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class TaskLoader {

    public static void loadTasksFromDB(){
        System.out.println("Loading tasks from DB:");

        Date d = new Date();
        long current =  d.getTime();
        for(final Task task :Task.find.all()){
            final long seconds = (task.executeTime.getTime() - current)/1000;
            if(seconds < 0) {
                task.delete();

            }else{
                System.out.println("prižigam čez " + seconds + " sekund");

                Akka.system().scheduler().scheduleOnce(
                        Duration.create(seconds, TimeUnit.SECONDS),
                        new Runnable() {
                            public void run() {
                                System.out.println("Sem prižgal " + task.taskId );
                            }
                        }, Akka.system().dispatcher()
                );
            }
        }
    }
}
