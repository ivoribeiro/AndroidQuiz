package pt.ipp.estg.cmu.models;

public class Nivel {
    private String numero;
    private int pontuacaoBase;
    private int pontuacaoBaseErrada;
    private int pontuacaoHint;
    private boolean bloqueado;
    private int nPerguntas;
    private int nPerguntasResp;


    public Nivel(String numero, int nPerguntas, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint, boolean bloqueado) {
        this.numero = numero;
        this.nPerguntas = nPerguntas;
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

    public boolean isBloqueado() {
        return bloqueado;
    }

    public int getnPerguntas() {
        return nPerguntas;
    }

    public int getnPerguntasResp() {
        return nPerguntasResp;
    }

    public String getNumero() {
        return numero;
    }
}
