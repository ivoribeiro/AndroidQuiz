package pt.ipp.estg.cmu.models;

public class Nivel {
    private String numero;
    private int pontuacaoBase;
    private int pontuacaoBaseErrada;
    private int pontuacaoHint;

    public Nivel(String numero, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint) {
        this.numero = numero;
        this.pontuacaoBase = pontuacaoBase;
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
        this.pontuacaoHint = pontuacaoHint;
    }

    public String getNumber() {
        return numero;
    }

    public int getPontuacaoBase() {
        return pontuacaoBase;
    }

    public int getPontuacaoBaseErrada() {
        return pontuacaoBaseErrada;
    }

    public int getPontuacaoHint() {
        return pontuacaoHint;
    }

}
