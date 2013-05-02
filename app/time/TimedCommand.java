package time;

import akka.actor.UntypedActor;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 3.3.2013
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class TimedCommand extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println(message);
    }
}
