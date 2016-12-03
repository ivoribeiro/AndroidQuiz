package pt.ipp.estg.cmu.db.repositories;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;

public class PerguntaRepo extends Repo implements RepositoryInterface<Pergunta> {
    public PerguntaRepo(Context context) {
        super(context, "pergunta");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("CATEGORIA", "categoria");
        this.addField("IMAGEM", "imagem");
        this.addField("RESPOSTA", "respostaCerta");
        this.addField("RESPOSTAS_ERRADAS", "nRespostasErradas");
        this.addField("ACERTOU", "acertou");
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
        return null;
    }

    public ArrayList<Pergunta> getAllByNivel(int idNivel) {
        return this.getAllByField("nivel", "" + idNivel + "");
    }

}
