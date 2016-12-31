package pt.ipp.estg.cmu.server;

/**
 * Created by Navega on 12/31/2016.
 */
public class JsonBuilder {


    public static String BUILD_PLAYER(String username, String avatar) {
        String result = "{\"username\": \"" + username + "\"," +
                "\"avatar\": \"" + avatar + "\"," + "\"}";
        return result;
    }

}
