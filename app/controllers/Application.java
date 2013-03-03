package controllers;

import comunication.Comunicator;
import models.Command;
import models.HomeUser;
import models.Pin;
import play.mvc.*;


import views.html.*;


@Security.Authenticated(Secured.class)
public class Application extends Controller {

    private static String ARDUINO_URL = "http://arduino.gorsha.si:7670";

    public static Result index() {
        String userName =  request().username();

        return ok(index.render(HomeUser.find.where().eq("userName", userName).findUnique()));
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



}
