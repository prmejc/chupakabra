package comunication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.3.2013
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class Comunicator {

    private static String ARDUINO_URL = "http://arduino.gorsha.si:7670";

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
            response = e.getMessage();
        }
        return response;
    }

    public static void writePin(Long pinId, int status) {
        sendCommand("write", pinId + "-" + status);
    }
}
