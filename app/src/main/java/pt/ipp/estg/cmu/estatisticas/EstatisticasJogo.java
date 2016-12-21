package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;

public class EstatisticasJogo {

    private Context mContext;
    private PerguntaRepo mPerguntaRepo;
    private CategoriaRepo mCategoriaRepo;
    private NivelRepo mNivelRepo;
    private ArrayList<Categoria> categoriasJogo;


    //Indicadores do jogo
    private int pontuacao;
    private int pontuacaoGanha;
    private int pontuacaoPerdida;
    private int respostasErradas;
    private int respostasCertas;
    private int nPerguntas;


    public EstatisticasJogo(Context context) {

        this.mContext = context;
        this.mPerguntaRepo = new PerguntaRepo(context);
        this.mCategoriaRepo = new CategoriaRepo(context);
        this.mNivelRepo = new NivelRepo(context);
        this.categoriasJogo = mCategoriaRepo.getAll();

        for (Categoria categoria : categoriasJogo) {
            EstatisticasCategoria estatisticasCategoria = new EstatisticasCategoria(mContext, categoria.getNome());
            this.pontuacao += estatisticasCategoria.getPontuacao();
            this.pontuacaoGanha += estatisticasCategoria.getPontuacaoGanha();
            this.pontuacaoPerdida += estatisticasCategoria.getPontuacaoPerdida();
            this.respostasCertas += estatisticasCategoria.getnRespostasCertas();
            this.respostasErradas += estatisticasCategoria.getnRespostasErradas();
            this.nPerguntas += estatisticasCategoria.getnPerguntas();
        }
    }

    public int getnPerguntas() {
        return this.nPerguntas;
    }

    /**
     * Retorna a soma de todas as pontuacoes da categorias
     *
     * @return
     */
    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Retorna o numer de respostas erradas dadas no jogo fazendo uma query que faz o sumatorio
     * do numero das respostas erradas de todas as perguntas do jogo
     *
     * @return
     */
    public int getnRespostasErradas() {
        return respostasErradas;
    }

    /**
     * Retorna o numer de respostas certas dadas no jogo fazendo uma query que faz o sumatorio
     * do numero das respostas certas de todas as perguntas do jogo
     *
     * @return
     */
    public int getnRespostasCertas() {
        return respostasCertas;
    }

    public int getnAjudasUsadas() {
        return 0;
    }

    /**
     * Retorna toda a pontuacao ganha no jogo somando a pontuacao ganha nas categorias
     *
     * @return
     */
    public int getPontuacaoGanha() {
        return this.pontuacaoGanha;
    }

    /**
     * Retorna toda a pontuacao perdida no jogo somando a pontuacao perdida nas categorias
     *
     * @return
     */
    public int getPontuacaoPerdida() {
        return pontuacaoPerdida;
    }
}
