package pt.ipp.estg.cmu.interfaces;

/**
 * @author 8130031
 * @author 8130258
 */
public interface GameInterfaceListener {
    void setAnswered(boolean hit);

    void unlock();

    void setHint(int hints);

    void setScore(int score);

}
