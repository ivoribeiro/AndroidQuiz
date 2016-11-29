package pt.ipp.estg.cmu.models;

public class Jogador {
    private String username;
    private int pontuacao;
    private int nAjudasRestantes;

    public Jogador(String username, int pontuacao, int nAjudasRestantes) {
        this.username = username;
        this.pontuacao = pontuacao;
        this.nAjudasRestantes = nAjudasRestantes;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getnAjudasRestantes() {
        return nAjudasRestantes;
    }

    public String getUsername() {
        return username;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setnAjudasRestantes(int nAjudasRestantes) {
        this.nAjudasRestantes = nAjudasRestantes;
    }
}
