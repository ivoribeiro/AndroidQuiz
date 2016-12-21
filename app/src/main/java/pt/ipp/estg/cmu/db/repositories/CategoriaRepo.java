package pt.ipp.estg.cmu.db.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;

public class CategoriaRepo extends Repo implements RepositoryInterface<Categoria> {

    NivelRepo mNivelRepo;

    public CategoriaRepo(Context context) {
        super(context, "categoria");
        this.setFields();
        this.mNivelRepo = new NivelRepo(context);
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("NOME", "nome");
        this.addField("ATIVA", "ativa");
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

    /**
     * Faz update a uma pergunta por id
     *
     * @param categoria
     * @return
     */
    public Categoria updateCategoria(Categoria categoria) {
        SQLiteDatabase db = super.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(categoria.getId())};
        ContentValues values = new ContentValues();
        values.put(this.getField("NOME"), categoria.getNome());
        values.put(this.getField("ATIVA"), categoria.isAtiva());
        db.update(this.getTable(), values, where, whereArgs);
        db.close();
        return categoria;
    }

    /**
     * Retorna a pontuacao total de uma categoria
     *
     * @param categoria
     * @return
     */
    public int getPontuacaoCategoria(String categoria) {
        int pontuacao = 0;
        ArrayList<Nivel> niveis = this.mNivelRepo.getAllByCategoria(categoria);
        for (Nivel nivel : niveis) {
            pontuacao += nivel.getPontuacao();
        }
        return pontuacao;
    }
//TODO retirar daqui e deixar so nas estatisticas do jogo
    /**
     * Retorna a soma de todas as pontuacoes da categorias
     *
     * @return
     */
    public int getPontuacaoJogo() {
        int pontuacao = 0;
        ArrayList<Categoria> categorias = this.getAll();
        for (Categoria categoria : categorias) {
            pontuacao+=this.getPontuacaoCategoria(categoria.getNome());
        }
        return pontuacao;
    }

}
