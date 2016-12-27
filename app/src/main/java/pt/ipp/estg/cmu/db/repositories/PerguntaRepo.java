package pt.ipp.estg.cmu.db.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.dbUtil;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;

public class PerguntaRepo extends Repo<Pergunta> implements RepositoryInterface<Pergunta> {
    public PerguntaRepo(Context context) {
        super(context, Pergunta.TABLE);
    }

    @Override
    public ArrayList<Pergunta> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.query(super.getTable(), tableColumns, whereClause, whereArgs,
                null, null, orderBy);
        ArrayList<Pergunta> perguntas = new ArrayList<>();
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
                pergunta.setRespostaActual(cursor.getString(7));
                pergunta.setnAjudasUsadas(cursor.getInt(8));
                perguntas.add(pergunta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return perguntas;
    }

    /**
     * Retorta todas as perguntas de um determinado nivel
     *
     * @param idNivel
     * @return
     */
    public ArrayList<Pergunta> getAllByNivel(int idNivel) {
        return super.getAllByField("nivel", "" + idNivel + "");
    }


    @Override
    public Pergunta insertInto(Pergunta pergunta) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Pergunta.NIVEL, pergunta.getNivel());
        values.put(Pergunta.IMAGEM, pergunta.getImagem());
        values.put(Pergunta.RESPOSTA, pergunta.getRespostaCerta());
        values.put(Pergunta.RESPOSTAS_ERRADAS, 0);
        values.put(Pergunta.ACERTOU, 0);
        values.put(Pergunta.STRING_ALEATORIA, pergunta.getStringAleatoria());
        values.put(Pergunta.RESPOSTA_ACTUAL, "");
        values.put(Pergunta.N_AJUDAS_USADAS, 0);
        db.insert(this.getTable(), null, values);
        db.close();
        return pergunta;
    }

    /**
     * Faz update a uma pergunta por id
     *
     * @param pergunta
     * @return
     */
    @Override
    public Pergunta update(Pergunta pergunta) {
        SQLiteDatabase db = super.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(pergunta.getId())};
        ContentValues values = new ContentValues();
        values.put(Pergunta.NIVEL, pergunta.getNivel());
        values.put(Pergunta.IMAGEM, pergunta.getImagem());
        values.put(Pergunta.RESPOSTA, pergunta.getRespostaCerta());
        values.put(Pergunta.RESPOSTAS_ERRADAS, pergunta.getnRespostasErradas());
        values.put(Pergunta.ACERTOU, pergunta.acertou());
        values.put(Pergunta.STRING_ALEATORIA, pergunta.getStringAleatoria());
        values.put(Pergunta.RESPOSTA_ACTUAL, pergunta.getRespostaActual());
        values.put(Pergunta.N_AJUDAS_USADAS, pergunta.getnAjudasUsadas());
        db.update(this.getTable(), values, where, whereArgs);
        db.close();
        return pergunta;
    }

    @Override
    public boolean canDelete(Pergunta pergunta) {
        return pergunta.acertou() == false && pergunta.getnAjudasUsadas() == 0 && pergunta.getnRespostasErradas() == 0;
    }

    /**
     * Retorna o numero de registos de uma query
     *
     * @param query
     * @return
     */
    public int count(String query) {
        int num = 0;
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            num = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return num;
    }

    /**
     * Retorna o numero de perguntas existentes
     *
     * @return
     */
    public int countAll() {
        String query = "SELECT count(id) FROM pergunta;";
        return count(query);
    }


    /**
     * Retorna o numero de perguntas existentes num nivel
     *
     * @return
     */
    public int countAllByNivel(int nivel) {
        String query = "SELECT count(id) FROM pergunta WHERE nivel=" + nivel + ";";
        return count(query);
    }

    /**
     * Retorna o numero de perguntas que forram respondidas corretamente
     *
     * @return
     */
    public int countCertas() {
        String query = "SELECT count(id) FROM pergunta WHERE acertou = 1;";
        return count(query);
    }


    /**
     * Retorna o numero de perguntas que forram respondidas corretamente
     *
     * @return
     */
    public int countCertasNivel(int nivel) {
        String query = "SELECT count(id) FROM pergunta WHERE acertou = 1 AND nivel=" + nivel + ";";
        return count(query);
    }

    /**
     * Soma do numero de todas as respostas erradas
     *
     * @return
     */
    public int sumErradas() {
        String query = "SELECT sum(nRespostasErradas) FROM pergunta;";
        return count(query);
    }

    /**
     * Retorna a soma de todas as respostas erradas no nivel
     *
     * @param nivel
     * @return
     */
    public int getSumNivelErradas(int nivel) {
        String query = "SELECT sum(nRespostasErradas) FROM pergunta WHERE nivel =" + nivel + ";";
        return count(query);
    }

    /**
     * Retorna a soma de todas as ajudas usadas no nivel
     *
     * @param nivel
     * @return
     */
    public int getSumNivelAjudasUsadas(int nivel) {
        String query = "SELECT sum(nAjudasUsadas) FROM pergunta WHERE nivel =" + nivel + ";";
        return count(query);
    }

    public void deleteByNivel(int id) {
        String query = dbUtil.deleteByFieldQueryString(this.getTable(), "nivel", "" + id);
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
