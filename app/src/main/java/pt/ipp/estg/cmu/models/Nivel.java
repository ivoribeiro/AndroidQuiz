package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Nivel implements Parcelable {

    private int id;
    private String numero;
    private String categoria;
    private int pontuacaoBase;
    private int pontuacaoBaseErrada;
    private int pontuacaoHint;
    private boolean bloqueado;
    private int nPerguntas;
    private int nPerguntasResp;


    public Nivel(int id, String numero, int nPerguntas, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint, boolean bloqueado, String categoria) {
        this.id = id;
        this.numero = numero;
        this.categoria = categoria;
        this.nPerguntas = nPerguntas;
        this.pontuacaoBase = pontuacaoBase;
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
        this.pontuacaoHint = pontuacaoHint;
        this.bloqueado = bloqueado;
        this.nPerguntas = 0;
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

        this.bloqueado = bloqueado == 1 ? true : false;
    }

    public int getnPerguntas() {
        return nPerguntas;
    }


    public void setnPerguntas(int nPerguntas) {
        this.nPerguntas = nPerguntas;
    }

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
        parcel.writeInt(nPerguntasResp);

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
        nPerguntas = in.readInt();
        nPerguntasResp = in.readInt();
        categoria = in.readString();

    }

}
