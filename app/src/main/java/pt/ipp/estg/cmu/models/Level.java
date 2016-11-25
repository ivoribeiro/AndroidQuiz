package pt.ipp.estg.cmu.models;

/**
 * Created by navega on 11/19/16.
 */

public class Level {
    private String number;
    private int pontuacaoBase;
    private int pontuacaoBaseErrada;
    private int pontuacaoHint;


    public Level(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public int getPontuacaoBase() {
        return pontuacaoBase;
    }

    public void setPontuacaoBase(int pontuacaoBase) {
        this.pontuacaoBase = pontuacaoBase;
    }

    public int getPontuacaoBaseErrada() {
        return pontuacaoBaseErrada;
    }

    public void setPontuacaoBaseErrada(int pontuacaoBaseErrada) {
        this.pontuacaoBase = pontuacaoBaseErrada;
    }

    public int getPontuacaoHint() {
        return pontuacaoHint;
    }

    public void setPontuacaoHint(int pontuacaoHint) {
        this.pontuacaoHint = pontuacaoHint;
    }


}
