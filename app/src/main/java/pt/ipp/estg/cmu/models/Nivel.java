package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Nivel implements Parcelable {

    private int id;
    private String numero;
    private String categoria;
    private int pontuacaoBase;// pontuação de cada pergunta deste nivel
    private int pontuacaoBaseErrada;// pontuacao retirada por cada pergunta errada neste nivel
    private int pontuacaoHint;//pontuacao retirada por cada ajuda usada neste nivel
    private boolean bloqueado;//estado do nivel, se o user ja desbloqueou o anterior ou nao
    private int nPerguntas;//numero de perguntas do nivel

    //Prisão suspensa até novas ordens
    //private int nRespostasCertas;//numero de respostas certas no nivel
    //private int nMinRespostasCertas; //numero minimo de respostas certas para desbloquer o proximo nivel
    //private int nPerguntasResp;////numero de perguntas que o user ja respondeu neste nivel
    //TODO unlockNextLevelAt  numero minimo de questos respondidas para desbloquer o proximo nivel
    //TODO nAjudasTotal  numero de ajudas total para este nivel
    //TODO nPontuacaoTotal numero de pontos ganhos neste nivel
    //TODO nPerguntasCertas // numero de perguntas que o user acertou neste nivel

    private int nAjudas;
    private int pontuacao;

    public Nivel(int id, String numero, int nPerguntas, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint, boolean bloqueado, String categoria,int nAjudas,int pontuacao) {
        this.id = id;
        this.numero = numero;
        this.categoria = categoria;
        this.nPerguntas = nPerguntas;
        this.pontuacaoBase = pontuacaoBase;
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
        this.pontuacaoHint = pontuacaoHint;
        this.bloqueado = bloqueado;

        this.nAjudas = nAjudas;
        this.pontuacao = pontuacao;
    }

    public Nivel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }


    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
    }

    public int getPontuacaoHint() {
        return pontuacaoHint;
    }


    public void setPontuacaoHint(int pontuacaoHint) {
        this.pontuacaoHint = pontuacaoHint;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }


    public void setBloqueado(int bloqueado) {

        this.bloqueado = bloqueado == 1;
    }

    public int getnPerguntas() {
        return nPerguntas;
    }

    public void setnPerguntas(int nPerguntas) {
        this.nPerguntas = nPerguntas;
    }
/*

    public int getnPerguntasResp() {
        return nPerguntasResp;
    }

    public void setnPerguntasResp(int nPerguntasResp) {
        this.nPerguntasResp = nPerguntasResp;
    }

    public void addnPerguntas() {
        ++this.nPerguntas;
    }

    public void addnPerguntasResp() {
        ++this.nPerguntasResp;
    }
*/

    public int getnAjudas() {
        return nAjudas;
    }

    public void setnAjudas(int nAjudas) {
        this.nAjudas = nAjudas;
    }

    public void decrementnAjudas() {
        this.nAjudas--;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void addPontuacao(int pontuacao) {
        this.pontuacao += pontuacao;
    }

    ////////////////////// PARCEABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(numero);
        parcel.writeInt(pontuacaoBase);
        parcel.writeInt(pontuacaoBaseErrada);
        parcel.writeInt(pontuacaoHint);
        parcel.writeValue(bloqueado);
        parcel.writeString(categoria);
        parcel.writeInt(nPerguntas);
        //parcel.writeInt(nPerguntasResp);
        parcel.writeInt(nAjudas);
        parcel.writeInt(pontuacao);


    }

    public static final Parcelable.Creator<Nivel> CREATOR
            = new Parcelable.Creator<Nivel>() {
        public Nivel createFromParcel(Parcel in) {
            return new Nivel(in);
        }

        public Nivel[] newArray(int size) {
            return new Nivel[size];
        }

    };

    private Nivel(Parcel in) {
        numero = in.readString();
        pontuacaoBase = in.readInt();
        pontuacaoBaseErrada = in.readInt();
        pontuacaoHint = in.readInt();
        bloqueado = (Boolean) in.readValue(null);
        categoria = in.readString();
        nPerguntas = in.readInt();
        //nPerguntasResp = in.readInt();
        nAjudas = in.readInt();
        pontuacao = in.readInt();

    }

}
