package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Nivel;

public class EstatisticasCategoria {

    private NivelRepo mNivelRepo;
    private CategoriaRepo mCategoriaRepo;
    private String categoria;
    private Context context;
    private ArrayList<Nivel> niveisCategoria;

    private int pontuacao;
    private int pontuacaoGanha;
    private int pontuacaoPerdida;
    private int respostasErradas;
    private int respostasCertas;
    private int nperguntas;

    public EstatisticasCategoria(Context context, String categoria) {
        mNivelRepo = new NivelRepo(context);
        mCategoriaRepo = new CategoriaRepo(context);
        this.categoria = categoria;
        this.context = context;
        this.niveisCategoria = mNivelRepo.getAllByCategoria(this.categoria);

        for (Nivel nivel : niveisCategoria) {
            EstatisticasNivel estatisticasNivel = new EstatisticasNivel(context, nivel.getId());
            pontuacao += estatisticasNivel.getPontuacao();
            pontuacaoGanha += estatisticasNivel.getPontuacaoGanha();
            pontuacaoPerdida += estatisticasNivel.getPontuacaoPerdida();
            respostasErradas += estatisticasNivel.getnRespostasErradas();
            respostasCertas += estatisticasNivel.getnRespostasCertas();
            nperguntas += estatisticasNivel.getnPerguntas();
        }
    }

    /**
     * Retorna a pontuacao na categoria
     *
     * @return
     */
    public int getPontuacao() {
        return this.pontuacao;
    }

    /**
     * Retorna o numero de perguntas da categoria somando o numero de perguntas
     * de todos os niveis da categoria
     *
     * @return
     */
    public int getnPerguntas() {
        return nperguntas;
    }

    /**
     * Retorna a pontuacao ganha nesta categoria somando a pontuacao ganha em todos os niveis da categoria
     *
     * @return
     */
    public int getPontuacaoGanha() {
        return pontuacaoGanha;
    }

    /**
     * Retorna a pontuacao perdida nesta categoria somando a pontuacao perdida em todos os niveis da categoria
     *
     * @return
     */
    public int getPontuacaoPerdida() {
        return pontuacaoPerdida;
    }

    /**
     * Retorna o numero de respostas erradas na categoria somando
     * todas o numero das respostas erradas de cada nivel da categoria
     *
     * @return
     */
    public int getnRespostasErradas() {
        return respostasErradas;
    }

    /**
     * Retorna o numero de respostas certas na categoria somando
     * todas o numero das respostas certas de cada nivel da categoria
     *
     * @return
     */
    public int getnRespostasCertas() {

        return respostasCertas;
    }
}
