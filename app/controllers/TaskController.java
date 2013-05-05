package controllers;

import models.HomeUser;
import models.Task;
import play.mvc.*;
import views.html.tasks;


/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.5.2013
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
public class TaskController extends Application {

    public static Result tasks() {
        String userName =  request().username();

        return ok(tasks.render(HomeUser.find.where().eq("userName", userName).findUnique(), Task.find.all()));
    }

    public static Result saveTask() {
        String userName =  request().username();

        return ok();
    }

}
