package pt.ipp.estg.cmu.db.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Pergunta;

public class PerguntaRepo extends Repo implements RepositoryInterface<Pergunta> {
    public PerguntaRepo(Context context) {
        super(context, "pergunta");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("NIVEL", "nivel");
        this.addField("IMAGEM", "imagem");
        this.addField("RESPOSTA", "respostaCerta");
        this.addField("RESPOSTAS_ERRADAS", "nRespostasErradas");
        this.addField("ACERTOU", "acertou");
        this.addField("STRING_ALEATORIA", "stringAleatoria");
    }

    @Override
    public ArrayList<Pergunta> getAll() {
        return null;
    }

    @Override
    public ArrayList<Pergunta> getAllByField(String field, String value) {
        String query = this.getAllByFieldQueryString(field, value);
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Pergunta pergunta = new Pergunta();
                pergunta.setId(cursor.getInt(0));
                pergunta.setNivel(cursor.getInt(1));
                pergunta.setImagem(cursor.getString(2));
                pergunta.setRespostaCerta(cursor.getString(3));
                pergunta.setnRespostasErradas(cursor.getInt(4));
                pergunta.setAcertou(cursor.getInt(5) == 1 ? true : false);
                pergunta.setStringAleatoria(cursor.getString(6));
                perguntas.add(pergunta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return perguntas;

    }

    @Override
    public Pergunta getById(int id) {
        return null;
    }

    @Override
    public Pergunta insertInto(Pergunta pergunta) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.getField("NIVEL"), pergunta.getNivel());
        values.put(this.getField("IMAGEM"), pergunta.getImagem());
        values.put(this.getField("RESPOSTA"), pergunta.getRespostaCerta());
        values.put(this.getField("RESPOSTAS_ERRADAS"), 0);
        values.put(this.getField("ACERTOU"), 0);
        values.put(this.getField("STRING_ALEATORIA"), pergunta.getStringAleatoria());
        db.insert(this.getTable(), null, values);
        db.close();
        return pergunta;
    }

    @Override
    public void deleteById(int id) {
        String query = this.deleteByFieldQueryString("id", "" + id);
        SQLiteDatabase db = super.getWritableDatabase();
        db.rawQuery(query, null);
    }

    /**
     * Retorta todas as perguntas de um determinado nivel
     *
     * @param idNivel
     * @return
     */
    public ArrayList<Pergunta> getAllByNivel(int idNivel) {
        return this.getAllByField("nivel", "" + idNivel + "");
    }

    /**
     * Faz update a uma pergunta por id
     *
     * @param pergunta
     * @return
     */
    public Pergunta updatePergunta(Pergunta pergunta) {
        SQLiteDatabase db = super.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(pergunta.getId())};
        ContentValues values = new ContentValues();
        values.put(this.getField("NIVEL"), pergunta.getNivel());
        values.put(this.getField("IMAGEM"), pergunta.getImagem());
        values.put(this.getField("RESPOSTA"), pergunta.getRespostaCerta());
        values.put(this.getField("RESPOSTAS_ERRADAS"), pergunta.getnRespostasErradas());
        values.put(this.getField("ACERTOU"), pergunta.acertou());
        db.update(this.getTable(), values, where, whereArgs);
        db.close();
        return pergunta;
    }
}
