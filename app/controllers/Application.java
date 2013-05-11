package controllers;

import comunication.Comunicator;
import models.Command;
import models.HomeUser;
import models.Pin;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin;
import views.html.index;
import views.html.log;


@Security.Authenticated(Secured.class)
public class Application extends Controller {

    public static Result index() {
        String userName =  request().username();
        return ok(index.render(HomeUser.find.where().eq("userName", userName).findUnique()));
    }

    public static Result admin() {
        String userName =  request().username();

        return ok(admin.render(HomeUser.find.where().eq("userName", userName).findUnique()));
    }

    public static Result log() {

        String userName =  request().username();

        return ok(log.render(
                                Command.find.where().orderBy("dateCreate desc").findList(),
                                HomeUser.find.where().eq("userName", userName).findUnique()
        ));
    }

    public static Result command(String command, String sPin) {

        if(!command.equals("status")){
            Long pinId = Long.parseLong(sPin.split("-")[0].trim());
            int pinStatus = Integer.parseInt(sPin.split("-")[1].trim());

            Pin pin = Pin.find.byId(pinId);
            pin.status = pinStatus;
            pin.save();
            System.out.println(pin.dateCreate + " " + pin.pinId);
            Command cmd = new Command(pinId, pinStatus, HomeUser.find.byId(request().username()));
            cmd.save();
        }

        String response = Comunicator.checkStatus();
        return ok(response);
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes",
                routes.javascript.UserController.deleteUser()
        ));
    }



}
