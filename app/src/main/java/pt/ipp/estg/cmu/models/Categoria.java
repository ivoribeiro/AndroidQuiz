package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by navega on 11/30/16.
 */

public class Categoria implements Parcelable {
    private int id;
    private String nome;

    public Categoria(int id, String nome) {
        this.id = id;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
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
    }
}
