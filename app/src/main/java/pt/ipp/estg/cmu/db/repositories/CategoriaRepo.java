package pt.ipp.estg.cmu.db.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Categoria;

public class CategoriaRepo extends Repo implements RepositoryInterface<Categoria> {


    public CategoriaRepo(Context context) {
        super(context, "categoria");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("NOME", "nome");
    }


    @Override
    public ArrayList<Categoria> getAll() {
        String query = this.getAllQueryString();
        ArrayList<Categoria> categorias = new ArrayList<>();
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1));
                categorias.add(categoria);
            } while (cursor.moveToNext());
            return categorias;
        }
        return null;
    }

    @Override
    public Categoria getById(int id) {
        String query = this.getByIdQueryString(id);
        return null;
    }
}
