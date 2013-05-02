import com.avaje.ebean.Ebean;
import comunication.Syncer;
import models.HomeUser;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;
import time.TaskLoader;

import java.util.List;

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
            Ebean.save((List) Yaml.load("seed.yml"));
        }


        Syncer.arduino2Db();
        Syncer.db2Arduino();

        TaskLoader.loadTasksFromDB();
    }

}
