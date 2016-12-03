package pt.ipp.estg.cmu.db.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Jogador;

/**
 * Created by ivoribeiro on 01-12-2016.
 */

public class JogadorRepo extends Repo implements RepositoryInterface<Jogador> {

    public JogadorRepo(Context context) {
        super(context, "jogador");
        this.setFields();
    }

    private void setFields() {
        this.addField("USER", "username");
        this.addField("PONTUACAO", "pontuacao");
        this.addField("DICAS", "dicas");
    }

    @Override
    public ArrayList<Jogador> getAll() {
        return null;
    }

    @Override
    public ArrayList<Jogador> getAllByField(String field, String value) {
        return null;
    }

    @Override
    public Jogador getById(int id) {
        SQLiteDatabase db = super.getReadableDatabase();
        String query = this.getByIdQueryString(1);
        Cursor cursor = db.rawQuery(query, null);
        Jogador jogador = null;
        if (cursor != null) {
            cursor.moveToFirst();
            jogador = new Jogador(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        }
        cursor.close();
        db.close();
        return jogador;
    }


    @Override
    public Jogador insertInto(Jogador jogador) {
        return null;
    }
}
