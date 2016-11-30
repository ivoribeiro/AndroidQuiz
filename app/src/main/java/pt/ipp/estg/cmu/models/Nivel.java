package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Nivel implements Parcelable  {
    private int id;
    private String numero;
    private String categoria;
    private int pontuacaoBase;
    private int pontuacaoBaseErrada;
    private int pontuacaoHint;
    private boolean bloqueado;
    private int nPerguntas;
    private int nPerguntasResp;


    public Nivel(String numero, String categoria, int nPerguntas, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint, boolean bloqueado) {
        this.numero = numero;
        this.categoria = categoria;
        this.nPerguntas = nPerguntas;
        this.pontuacaoBase = pontuacaoBase;
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
        this.pontuacaoHint = pontuacaoHint;
        this.bloqueado = bloqueado;
        this.nPerguntas = 0;
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

    public void addnPerguntasResp() {
        ++this.nPerguntasResp;
    }

    public String getNumero() {
        return numero;
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

    }
}
