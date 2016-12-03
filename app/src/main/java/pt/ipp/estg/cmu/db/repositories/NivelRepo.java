package pt.ipp.estg.cmu.db.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Nivel;

public class NivelRepo extends Repo implements RepositoryInterface<Nivel> {

    public NivelRepo(Context context) {
        super(context, "nivel");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("NUMERO", "numero");
        this.addField("CATEGORIA", "categoria");
        this.addField("BLOQUEADO", "bloqueado");
        this.addField("N_PERGUNTAS", "nPerguntas");
        this.addField("PONTUACAO_CERTA", "pontuacaoBase");
        this.addField("PONTUACAO_ERRADA", "pontuacaoBaseErrada");
        this.addField("PONTUACAO_DICA", "pontuacaoHint");
        this.addField("N_RESPOSTAS_CERTAS", "nRespostasCertas");

    }

    @Override
    public ArrayList<Nivel> getAll() {
        return null;
    }

    @Override
    public ArrayList<Nivel> getAllByField(String field, String value) {
        String query = this.getAllByFieldQueryString(field, value);
        ArrayList<Nivel> niveis = new ArrayList<>();
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Nivel nivel = new Nivel();
                nivel.setId(cursor.getInt(0));
                nivel.setNumero(cursor.getString(1));
                nivel.setCategoria(cursor.getString(2));
                nivel.setBloqueado(cursor.getInt(3));
                nivel.setnPerguntas(cursor.getInt(4));
                nivel.setPontuacaoBase(cursor.getInt(5));
                nivel.setPontuacaoBaseErrada(cursor.getInt(6));
                nivel.setPontuacaoHint(cursor.getInt(7));
                nivel.setnPerguntasResp(cursor.getInt(8));
                niveis.add(nivel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return niveis;

    }

    @Override
    public Nivel getById(int id) {
        return null;
    }

    @Override
    public Nivel insertInto(Nivel nivel) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.getField("NUMERO"), nivel.getNumero());
        values.put(this.getField("CATEGORIA"), nivel.getCategoria());
        values.put(this.getField("BLOQUEADO"), nivel.isBloqueado() ? 1 : 0);
        values.put(this.getField("N_PERGUNTAS"), nivel.getnPerguntas());
        values.put(this.getField("PONTUACAO_CERTA"), nivel.getPontuacaoBase());
        values.put(this.getField("PONTUACAO_ERRADA"), nivel.getPontuacaoBaseErrada());
        values.put(this.getField("PONTUACAO_DICA"), nivel.getPontuacaoHint());
        values.put(this.getField("N_RESPOSTAS_CERTAS"), 0);
        db.insert(this.getTable(), null, values);
        db.close();
        return nivel;
    }

    public ArrayList<Nivel> getAllByCategoria(String categoria) {
        return this.getAllByField("categoria", "\'" + categoria + "\'");
    }
}
