package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Nivel implements Parcelable {
    private String numero;
    private int pontuacaoBase;//pontuacao base de cada pergunta deste nivel
    private int pontuacaoBaseErrada;//pontucao que desconta sempre que erre uma pergunta deste nivel
    private int pontuacaoHint;//pontuacao que desconta sempre que usa uma ajuda
    private boolean bloqueado;
    private int nPerguntas;//numero total de perguntas deste nivel
    private int nPerguntasResp;//numero de poerguntas respondidas neste nivel


    public Nivel(String numero, int nPerguntas, int pontuacaoBase, int pontuacaoBaseErrada, int pontuacaoHint, boolean bloqueado) {
        this.numero = numero;
        this.nPerguntas = nPerguntas;
        this.pontuacaoBase = pontuacaoBase;
        this.pontuacaoBaseErrada = pontuacaoBaseErrada;
        this.pontuacaoHint = pontuacaoHint;
        this.bloqueado = bloqueado;
        this.nPerguntasResp = 0;
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

    public String getNumero() {
        return numero;
    }

    public int getnPerguntasResp() {
        return nPerguntasResp;
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
