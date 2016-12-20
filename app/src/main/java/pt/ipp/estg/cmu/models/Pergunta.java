package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Pergunta implements Parcelable {

    private int id;
    private int nivel;
    private String imagem;
    private String respostaCerta;
    private int nRespostasErradas;
    private boolean acertou;
    private String stringAleatoria;
    private String respostaActual;


    public String getRespostaActual() {
        return respostaActual;
    }

    public void setRespostaActual(String respostaActual) {
        this.respostaActual = respostaActual;
    }


    public String getStringAleatoria() {
        return stringAleatoria;
    }

    public void setStringAleatoria(String stringAleatoria) {
        this.stringAleatoria = stringAleatoria;
    }

    public Pergunta(int nivel, String imagem, String respostaCerta) {
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

    public void setNivel(int nivel) {
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

    public int getNivel() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(nivel);
        parcel.writeString(imagem);
        parcel.writeString(respostaCerta);
        parcel.writeInt(nRespostasErradas);
        parcel.writeValue(acertou);
        parcel.writeString(stringAleatoria);
        parcel.writeString(respostaActual);


    }

    public static final Creator<Pergunta> CREATOR = new Creator<Pergunta>() {
        @Override
        public Pergunta createFromParcel(Parcel in) {
            return new Pergunta(in);
        }

        @Override
        public Pergunta[] newArray(int size) {
            return new Pergunta[size];
        }
    };

    protected Pergunta(Parcel in) {
        id = in.readInt();
        nivel = in.readInt();
        imagem = in.readString();
        respostaCerta = in.readString();
        nRespostasErradas = in.readInt();
        acertou = in.readByte() != 0;
        stringAleatoria = in.readString();
        respostaActual = in.readString();
    }
}
