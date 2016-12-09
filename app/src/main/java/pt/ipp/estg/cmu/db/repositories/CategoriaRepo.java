package pt.ipp.estg.cmu.db.repositories;

import android.content.ContentValues;
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
                Categoria categoria = new Categoria();
                categoria.setId(cursor.getInt(0));
                categoria.setNome(cursor.getString(1));
                categoria.setAtiva(cursor.getInt(2) == 1);
                categorias.add(categoria);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

    @Override
    public ArrayList<Categoria> getAllByField(String field, String value) {
        return null;
    }

    @Override
    public Categoria getById(int id) {
        String query = this.getByIdQueryString(id);
        return null;
    }


    @Override
    public Categoria insertInto(Categoria categoria) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.getField("NOME"), categoria.getNome());
        db.insert(this.getTable(), null, values);
        db.close();
        return categoria;
    }

    @Override
    public void deleteById(int id) {
        String query = this.deleteByFieldQueryString("id", "" + id);
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL(query);
    }
}
