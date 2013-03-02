package comunication;

import models.Pin;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.3.2013
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class Syncer {


    public static void arduino2Db(){
        String arduinoResponse = Comunicator.sendCommand("status", "0");

        String outPuts = arduinoResponse.split("out")[1];
        outPuts = outPuts.split("\\{")[1];
        outPuts = outPuts.split("\\}")[0];

        //"5":1,"6":0,"7":0
        for (String sPin : outPuts.split(",")){
            sPin = sPin.trim().replaceAll("\"", "").replaceAll(" ", "");
            int colonIndex = sPin.indexOf(":");
            Long pinId = Long.parseLong(sPin.substring(0,colonIndex));
            int pinStatus = Integer.parseInt(sPin.substring(colonIndex+1));
            if(Pin.find.byId(pinId) == null){
                Pin pin = new Pin(pinId, "OUT", pinStatus);
                pin.save();
            }

        }
    }

    public static void db2Arduino(){
        List<Pin> pins = Pin.find.where().eq("type", "OUT").findList();
        for (Pin pin : pins){
            Comunicator.writePin(pin.pinId, pin.status);
        }
    }
}
