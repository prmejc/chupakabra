package comunication;

import models.HomeUser;
import play.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.3.2013
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class Comunicator {

    private static String ARDUINO_URL = "http://84.255.226.118:7670";

    public static String checkStatus(){
        Syncer.db2Arduino();
        return sendCommand("status", "0");
    }

    public static String sendCommand(String command, String pin) {
        String response = "";
        try {
            URL arduino = new URL(ARDUINO_URL + "/" + command + "/" + pin);
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
            response = "Povezava z " + ARDUINO_URL + " neuspešna!";
        }
        return response;
    }

    public static void writePin(Long pinId, int status) {
        sendCommand("write", pinId + "-" + status);
    }

    public static void sendMail(){
        try{
            Logger.info("--------------mail----------------");
            MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
            mail.setSubject("Voda v kleti");
            List<HomeUser> list = HomeUser.find.all();
            for(HomeUser user: list){
                if(user.email != null)
                mail.addRecipient(user.getEmail());
            }

            mail.addFrom("Enarocanje <klet.senzor@gmail.com>");
            mail.sendHtml(
                    "<html><body><h1>Opozorilo!</h1><p>Voda v kleti!</p><body>");
        }catch (Exception e){
            Logger.error("Ne morem poslati emaila -> " + e.getMessage());
        }
    }
}
