package controllers;

import play.mvc.*;


import views.html.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Map;

@Security.Authenticated(Secured.class)
public class Application extends Controller {

    private static String ARDUINO_URL = "http://arduino.gorsha.si:7670";

    public static Result index() {

        return ok(index.render(""));
    }

    public static Result command(String command, String pin) {
        String response = sendCommand(command, pin);
        return ok(response);
    }

    private static String sendCommand(String command, String pin) {
        String response = "";
        try {
            URL arduino = new URL(ARDUINO_URL + "/" + command + "/" + pin);
            System.out.println(arduino);
            URLConnection yc = arduino.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response+=inputLine;
            in.close();
        }
        catch (Exception e){
            response = e.getMessage();
        }
        return response;
    }

}
