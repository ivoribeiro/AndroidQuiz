package pt.ipp.estg.cmu.models;

/**
 * Created by Navega on 12/30/2016.
 */

public class OnlineScore {

    private String username;
    private String score;
    private int avatar;

    public OnlineScore() {
    }

    public OnlineScore(String username, String score, int avatar) {
        this.username = username;
        this.score = score;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
