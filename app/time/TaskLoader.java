package time;

import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

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
        System.out.println("counting.........");

        Akka.system().scheduler().scheduleOnce(
                Duration.create(10, TimeUnit.SECONDS),
                new Runnable() {
                    public void run() {
                        System.out.println("...................done");
                    }
                }, Akka.system().dispatcher()
        );

    }
}
