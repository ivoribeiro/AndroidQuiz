package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {

    public static final String TABLE = "categoria";
    public final static String ID = "id";
    public final static String NOME = "nome";
    public final static String ATIVA = "ativa";
    public final static String IMAGEM = "imagem";

    private int id;
    private String nome;
    private boolean ativa;
    private String imagem;

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Categoria() {
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeByte((byte) (ativa ? 1 : 0));
    }

    public static final Parcelable.Creator<Categoria> CREATOR = new Parcelable.Creator<Categoria>() {
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    private Categoria(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        ativa = in.readByte() != 0;
    }

}
