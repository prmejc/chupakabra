import com.avaje.ebean.Ebean;
import comunication.Comunicator;
import comunication.Syncer;
import models.HomeUser;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.Yaml;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Worker: prmejc
 * Date: 11.2.2013
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {

        // Check if the database is empty
        if (HomeUser.find.findRowCount() == 0) {
            HomeUser hu = new HomeUser("prmejc", "secret", true);
            hu.save();
            Logger.info("geslo: " + hu.password);
            Ebean.save((List) Yaml.load("seed.yml"));
        }


        Syncer.arduino2Db();
        Syncer.db2Arduino();

        //TaskLoader.loadTasksFromDB();

        Logger.info("--------------taski----------------");


        Akka.system().scheduler().schedule(
                Duration.create(20, TimeUnit.SECONDS), Duration.create(12, TimeUnit.HOURS),
                new Runnable() {
                    public void run() {
                        String arduinoResponse = Comunicator.sendCommand("status", "0");
                        Logger.info("--------------arduinoResponse----------------");

                        if (arduinoResponse.indexOf("out") > 0) {
                            String inPuts = arduinoResponse.split("in")[1];
                            inPuts = inPuts.split("\\{")[1];
                            inPuts = inPuts.split("\\}")[0];
                            Logger.info("--------------sPin----------------");

                            //"5":1,"6":0,"7":0
                            for (String sPin : inPuts.split(",")) {
                                sPin = sPin.trim().replaceAll("\"", "").replaceAll(" ", "");
                                int colonIndex = sPin.indexOf(":");
                                Long pinId = Long.parseLong(sPin.substring(0, colonIndex));
                                int pinStatus = Integer.parseInt(sPin.substring(colonIndex + 1));
                                Logger.info(pinId + " " + pinStatus);

                                if(pinId == 2 && pinStatus == 1){
                                    Comunicator.sendMail();
                                }

                            }
                        }


                    }
                }, Akka.system().dispatcher()
        );

        Akka.system().scheduler().schedule(
                Duration.create(5, TimeUnit.MINUTES), Duration.create(5, TimeUnit.MINUTES),
                new Runnable() {
                    public void run() {
                        Syncer.db2Arduino();
                    }
                }, Akka.system().dispatcher()
        );
    }

}
