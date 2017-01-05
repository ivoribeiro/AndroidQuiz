package pt.ipp.estg.dblib.estatisticas;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.dblib.repositories.CategoriaRepo;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.models.Categoria;
import pt.ipp.estg.dblib.models.Nivel;

public class EstatisticasCategoria {

    private NivelRepo mNivelRepo;
    private CategoriaRepo mCategoriaRepo;
    private Categoria categoria;
    private Context context;
    private ArrayList<Nivel> niveisCategoria;

    private int pontuacao;
    private int pontuacaoGanha;
    private int pontuacaoPerdida;
    private int respostasErradas;
    private int respostasCertas;
    private int nperguntas;
    private int nAjudasUsadas;
    private int nperguntasporresponder;

    public int getNperguntasporresponder() {
        return nperguntasporresponder;
    }

    public EstatisticasCategoria(Context context, String categoria) {
        mNivelRepo = new NivelRepo(context);
        mCategoriaRepo = new CategoriaRepo(context);
        this.categoria = mCategoriaRepo.getByName(categoria);
        this.context = context;
        this.niveisCategoria = mNivelRepo.getAllByCategoria(categoria);
        this.pontuacao = 0;
        this.pontuacaoGanha = 0;
        this.pontuacaoPerdida = 0;
        this.respostasErradas = 0;
        this.respostasCertas = 0;
        this.nperguntas = 0;
        this.nAjudasUsadas = 0;
        this.nperguntasporresponder = 0;
        for (Nivel nivel : niveisCategoria) {
            EstatisticasNivel estatisticasNivel = new EstatisticasNivel(context, nivel);
            this.pontuacao += estatisticasNivel.getPontuacao();
            this.pontuacaoGanha += estatisticasNivel.getPontuacaoGanha();
            this.pontuacaoPerdida += estatisticasNivel.getPontuacaoPerdida();
            this.respostasErradas += estatisticasNivel.getnRespostasErradas();
            this.respostasCertas += estatisticasNivel.getnRespostasCertas();
            this.nperguntas += estatisticasNivel.getnPerguntas();
            this.nAjudasUsadas += estatisticasNivel.getnAjudasUsadas();
            if (nivel.isBloqueado() == false) {
                this.nperguntasporresponder += estatisticasNivel.getnPerguntasPorResponder();
            }
        }
    }

    public Categoria getCategoria() {
        return this.categoria;
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

    public int getAjudasUsadas() {
        return this.nAjudasUsadas;
    }
}
