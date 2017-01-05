package pt.ipp.estg.dblib.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Nivel implements Parcelable {

    public static final String TABLE = "nivel";

    public static final String ID_NIVEL = "id";
    public static final String NUMERO = "numero";
    public static final String CATEGORIA = "categoria";
    public static final String PONTUACAO_CERTA = "pontuacaoBase";
    public static final String PONTUACAO_ERRADA = "pontuacaoBaseErrada";
    public static final String PONTUACAO_DICA = "pontuacaoHint";
    public static final String BLOQUEADO = "bloqueado";
    public static final String N_AJUDAS = "nAjudas";
    public static final String PONTUACAO = "pontuacao";
    public static final String N_MIN_RESPOSTAS_CERTAS = "nMinRespostasCertas";

    private int id;
    private String numero;
    private String categoria;
    private int pontuacaoBase;// pontuação de cada pergunta deste nivel
    private int pontuacaoBaseErrada;// pontuacao retirada por cada pergunta errada neste nivel
    private int pontuacaoHint;//pontuacao retirada por cada ajuda usada neste nivel
    private boolean bloqueado;//estado do nivel, se o user ja desbloqueou o anterior ou nao
    private int nAjudas;// numero de ajudas restantes neste nivel
    private int pontuacao;//numero de pontos ganhos neste nivel
    private int nMinRespostasCertas; //numero minimo de respostas certas para desbloquer o proximo nivel

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

    public void removePontuacao(int pontuacao) {
        this.pontuacao -= pontuacao;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getnMinRespostasCertas() {
        return nMinRespostasCertas;
    }

    public void setnMinRespostasCertas(int nMinRespostasCertas) {
        this.nMinRespostasCertas = nMinRespostasCertas;
    }

    ////////////////////// PARCEABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(numero);
        parcel.writeInt(pontuacaoBase);
        parcel.writeInt(pontuacaoBaseErrada);
        parcel.writeInt(pontuacaoHint);
        parcel.writeValue(bloqueado);
        parcel.writeString(categoria);
        parcel.writeInt(nAjudas);
        parcel.writeInt(pontuacao);
        parcel.writeInt(nMinRespostasCertas);
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
        id = in.readInt();
        numero = in.readString();
        pontuacaoBase = in.readInt();
        pontuacaoBaseErrada = in.readInt();
        pontuacaoHint = in.readInt();
        bloqueado = (Boolean) in.readValue(null);
        categoria = in.readString();
        nAjudas = in.readInt();
        pontuacao = in.readInt();
        nMinRespostasCertas = in.readInt();

    }

}
