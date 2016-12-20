package pt.ipp.estg.cmu.interfaces;

public interface GameInterfaceListener {
    void setAnswered(boolean hit);

    void setHint(int hints);

    void setScore(int score);

}
