package pt.ipp.estg.cmu.server;

public class JsonBuilder {

    public static String BUILD_PLAYER(String username, String avatar) {
        String result = "{\"username\":\"" + username + "\",\"avatar\":\"" + avatar + "\"}";
        return result;
    }

}