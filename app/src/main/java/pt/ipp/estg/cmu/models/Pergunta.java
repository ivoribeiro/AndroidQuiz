package pt.ipp.estg.cmu.models;

public class Pergunta {

    private int id;
    private String nivel;
    private String imagem;
    private String respostaCerta;
    private int nRespostasErradas;
    private boolean acertou;


    public Pergunta(String nivel, String imagem, String respostaCerta) {
        this.nivel = nivel;
        this.imagem = imagem;
        this.respostaCerta = respostaCerta;
    }

    public Pergunta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }


    public void setRespostaCerta(String respostaCerta) {
        this.respostaCerta = respostaCerta;
    }

    public void setnRespostasErradas(int nRespostasErradas) {
        this.nRespostasErradas = nRespostasErradas;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public String getNivel() {
        return nivel;
    }

    public String getRespostaCerta() {
        return respostaCerta;
    }

    public String getImagem() {
        return imagem;
    }


    public boolean acertou() {
        return acertou;
    }

    public int getnRespostasErradas() {
        return nRespostasErradas;
    }

    public void addRespostasErradas() {
        this.nRespostasErradas++;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }
}
