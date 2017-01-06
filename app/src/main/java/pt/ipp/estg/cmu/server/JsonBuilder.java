package pt.ipp.estg.cmu.server;

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
