package pt.ipp.estg.dblib.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

import pt.ipp.estg.dblib.estatisticas.EstatisticasNivel;
import pt.ipp.estg.dblib.models.Nivel;

public class NivelRepo extends Repo<Nivel> implements RepositoryInterface<Nivel> {

    private Context mContext;
    private EstatisticasNivel estatisticasNivel;
    private PerguntaRepo mPerguntaRepo;

    public NivelRepo(Context context) {

        super(context, Nivel.TABLE);
        mContext = context;
        mPerguntaRepo = new PerguntaRepo(context);
    }

    @Override
    public ArrayList<Nivel> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.query(super.getTable(), tableColumns, whereClause, whereArgs,
                null, null, orderBy);
        ArrayList<Nivel> niveis = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Nivel nivel = new Nivel();
                nivel.setId(cursor.getInt(0));
                nivel.setNumero(cursor.getString(1));
                nivel.setCategoria(cursor.getString(2));
                nivel.setBloqueado(cursor.getInt(3));
                nivel.setPontuacaoBase(cursor.getInt(4));
                nivel.setPontuacaoBaseErrada(cursor.getInt(5));
                nivel.setPontuacaoHint(cursor.getInt(6));
                nivel.setnAjudas(cursor.getInt(7));
                nivel.setPontuacao(cursor.getInt(8));
                nivel.setnMinRespostasCertas(cursor.getInt(9));
                niveis.add(nivel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return niveis;
    }

    /**
     * Retorna todos os niveis com uma determinada categoria
     *
     * @param categoria
     * @return
     */
    public ArrayList<Nivel> getAllByCategoria(String categoria) {
        return super.getAllByField("categoria", categoria);
    }

    /**
     * Retorna todos os niveis bloqueados de uma determinada categoria
     *
     * @param categoria
     * @return
     */
    public ArrayList<Nivel> getBloquadosByCategoria(String categoria) {
        String[] fields = {"categoria", "bloqueado"};
        String[] values = {categoria, "1"};
        return super.getAllByFields(fields, values);

    }

    /**
     * Insere um novo nivel na base de dados
     *
     * @param nivel
     * @return
     */
    @Override
    public Nivel insertInto(Nivel nivel) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Nivel.NUMERO, nivel.getNumero());
        values.put(Nivel.CATEGORIA, nivel.getCategoria());
        values.put(Nivel.BLOQUEADO, nivel.isBloqueado() ? 1 : 0);
        values.put(Nivel.PONTUACAO_CERTA, nivel.getPontuacaoBase());
        values.put(Nivel.PONTUACAO_ERRADA, nivel.getPontuacaoBaseErrada());
        values.put(Nivel.PONTUACAO_DICA, nivel.getPontuacaoHint());
        values.put(Nivel.N_AJUDAS, nivel.getnAjudas());
        values.put(Nivel.PONTUACAO, 0);
        values.put(Nivel.N_MIN_RESPOSTAS_CERTAS, nivel.getnMinRespostasCertas());
        db.insert(this.getTable(), null, values);
        db.close();
        return nivel;
    }

    /**
     * Faz update a uma pergunta por id
     *
     * @param nivel
     * @return
     */

    @Override
    public Nivel update(Nivel nivel) {
        SQLiteDatabase db = super.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(nivel.getId())};
        ContentValues values = new ContentValues();
        values.put(Nivel.NUMERO, nivel.getNumero());
        values.put(Nivel.CATEGORIA, nivel.getCategoria());
        values.put(Nivel.PONTUACAO_CERTA, nivel.getPontuacaoBase());
        values.put(Nivel.PONTUACAO_ERRADA, nivel.getPontuacaoBaseErrada());
        values.put(Nivel.PONTUACAO_DICA, nivel.getPontuacaoHint());
        values.put(Nivel.BLOQUEADO, nivel.isBloqueado() ? 1 : 0);
        values.put(Nivel.N_AJUDAS, nivel.getnAjudas());
        values.put(Nivel.PONTUACAO, nivel.getPontuacao());
        values.put(Nivel.N_MIN_RESPOSTAS_CERTAS, nivel.getnMinRespostasCertas());
        db.update(this.getTable(), values, where, whereArgs);
        db.close();
        return nivel;
    }

    @Override
    public boolean canDelete(Nivel nivel) {
        estatisticasNivel = new EstatisticasNivel(mContext, nivel);
        return estatisticasNivel.getPontuacaoGanha() == 0 && estatisticasNivel.getPontuacaoPerdida() == 0;
    }

    //Delete the level and all the questions
    @Override
    public void deleteById(int id) {
        super.deleteById(id);
        mPerguntaRepo.deleteByNivel(id);
    }

    public Nivel getRandNivel() {
        ArrayList<Nivel> niveis = super.getAllByField("bloqueado", "0");
        ArrayList<Nivel> possiveis = new ArrayList<>();
        for (Nivel nivel: niveis){
            EstatisticasNivel estatisticasNivel =new EstatisticasNivel(mContext,nivel);
            if (estatisticasNivel.getnPerguntas()!=0){ possiveis.add(nivel);}
        }
        Random random = new Random();
        return possiveis.get(random.nextInt((possiveis.size() - 1) - 0 + 1) + 0);
    }
}
