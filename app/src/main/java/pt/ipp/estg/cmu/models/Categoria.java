package pt.ipp.estg.cmu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by navega on 11/30/16.
 */

public class Categoria implements Parcelable {
    private int id;
    private String name;

    public Categoria(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
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
        name = in.readString();
    }
}