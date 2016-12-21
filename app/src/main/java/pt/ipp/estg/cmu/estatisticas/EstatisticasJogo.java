package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Categoria;

public class EstatisticasJogo {

    private Context mContext;
    private PerguntaRepo mPerguntaRepo;
    private CategoriaRepo mCategoriaRepo;

    public EstatisticasJogo(Context context) {

        this.mContext = context;
        this.mPerguntaRepo = new PerguntaRepo(context);
        this.mCategoriaRepo = new CategoriaRepo(context);

    }


    public int getnPerguntas() {
        return mPerguntaRepo.countAll();
    }

    /**
     * Retorna a soma de todas as pontuacoes da categorias
     *
     * @return
     */
    public int getPontuacao() {
        int pontuacao = 0;
        ArrayList<Categoria> categorias = mCategoriaRepo.getAll();
        for (Categoria categoria : categorias) {
            pontuacao += mCategoriaRepo.getPontuacaoCategoria(categoria.getNome());
        }
        return pontuacao;
    }


    public int getnRespostasErradas() {
        return mPerguntaRepo.sumErradas();
    }

    public int getnRespostasCertas() {
        return mPerguntaRepo.countCertas();
    }

    public int getnAjudasUsadas() {
        return 0;
    }

}
