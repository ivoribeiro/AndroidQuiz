package pt.ipp.estg.cmu.db.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Categoria;

public class CategoriaRepo extends Repo<Categoria> implements RepositoryInterface<Categoria> {

    private NivelRepo mNivelRepo;

    public CategoriaRepo(Context context) {

        super(context, Categoria.TABLE);
        this.mNivelRepo = new NivelRepo(context);
    }

    @Override
    public ArrayList<Categoria> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy) {
        SQLiteDatabase db = super.getWritableDatabase();
        ArrayList<Categoria> categorias = new ArrayList<>();
        Cursor cursor = db.query(super.getTable(), tableColumns, whereClause, whereArgs,
                null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();
                categoria.setId(cursor.getInt(0));
                categoria.setNome(cursor.getString(1));
                categoria.setAtiva(cursor.getInt(2) == 1);
                categoria.setImagem(cursor.getString(3));
                categorias.add(categoria);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

    @Override
    public Categoria insertInto(Categoria categoria) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Categoria.ID, categoria.getNome());
        db.insert(this.getTable(), null, values);
        db.close();
        return categoria;
    }

    /**
     * Faz update a uma pergunta por id
     *
     * @param categoria
     * @return
     */
    @Override
    public Categoria update(Categoria categoria) {
        SQLiteDatabase db = super.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(categoria.getId())};
        ContentValues values = new ContentValues();
        values.put(Categoria.NOME, categoria.getNome());
        values.put(Categoria.ATIVA, categoria.isAtiva());
        db.update(this.getTable(), values, where, whereArgs);
        db.close();
        return categoria;

    }

    @Override
    public boolean canDelete(Categoria resource) {
        return false;
    }

    public Categoria getByName(String categoria) {
        return this.getAllByField("nome", categoria).get(0);

    }
}
