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
    }
}
