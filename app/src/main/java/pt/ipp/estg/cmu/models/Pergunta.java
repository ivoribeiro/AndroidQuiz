package pt.ipp.estg.cmu.models;

public class Pergunta {

    private int nivel;
    private String categoria;
    private String imagem;
    private String respostaCerta;

    private int nRespostasCertas;
    private int nRespostasErradas;
    private boolean acertou;


    public Pergunta(int nivel, String categoria, String imagem, String respostaCerta) {
        this.nivel = nivel;
        this.categoria = categoria;
        this.imagem = imagem;
        this.respostaCerta = respostaCerta;
    }

    public int getNivel() {
        return nivel;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getRespostaCerta() {
        return respostaCerta;
    }

    public String getImagem() {
        return imagem;
    }

    public int getnRespostasCertas() {
        return nRespostasCertas;
    }

    public boolean acertou() {
        return acertou;
    }

    public int getnRespostasErradas() {
        return nRespostasErradas;
    }

    public void addRespostasCertas() {
        this.nRespostasCertas++;
    }

    public void addRespostasErradas() {
        this.nRespostasErradas++;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }
}
