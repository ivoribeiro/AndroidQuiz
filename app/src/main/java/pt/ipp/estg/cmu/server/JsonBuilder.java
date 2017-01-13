package pt.ipp.estg.cmu.server;


/**
 * @author 8130031
 * @author 8130258
 */
public class JsonBuilder {

    public static String BUILD_PLAYER(String username, String avatar) {
        String result = "{\"username\":\"" + username + "\",\"avatar\":\"" + avatar + "\"}";
        return result;
    }

    public static String BUILD_UPDATE_SCORE(String username, int score) {
        String result = "{\"username\":\"" + username + "\",\"pontuacao\":" + score + "}";
        return result;
    }

}
