import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

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
    }

}
