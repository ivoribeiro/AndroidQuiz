package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;

public class EstatisticasNivel {

    private Context mContext;
    private NivelRepo mNivelRepo;
    private PerguntaRepo mPerguntaRepo;
    private Nivel nivel;

    public EstatisticasNivel(Context context, Nivel nivel) {
        this.mContext = context;
        this.mNivelRepo = new NivelRepo(context);
        this.mPerguntaRepo = new PerguntaRepo(context);
        this.nivel = nivel;
    }

    public Nivel getNivel() {
        return this.nivel;
    }

    /**
     * Retorna o numero de perguntas do nivel
     *
     * @return
     */
    public int getnPerguntas() {
        return mPerguntaRepo.countAllByNivel(nivel.getId());
    }

    /**
     * Retorna a pontuacao acumulada do nivel
     *
     * @return
     */
    public int getPontuacao() {
        return this.nivel.getPontuacao();
    }

    /**
     * Retorna o numero de respostas erradas nas perguntas do nivel
     *
     * @return
     */
    public int getnRespostasErradas() {
        return this.mPerguntaRepo.getSumNivelErradas(nivel.getId());
    }

    /**
     * Retorna o numero de respostas certas nas perguntas do nivel
     *
     * @return
     */
    public int getnRespostasCertas() {
        return this.mPerguntaRepo.countCertasNivel(nivel.getId());
    }

    /**
     * Retorna o numero de ajudas usadas nas perguntas do nivel
     *
     * @return
     */
    public int getnAjudasUsadas() {
        return this.mPerguntaRepo.getSumNivelAjudasUsadas(this.nivel.getId());
    }

    /**
     * Retorna toda a pontuacao ganha neste nivel fazendo o produto ao
     * numero de respostas certas e o valor de cada resposta certa de
     * uma pergunta do nivel
     *
     * @return
     */
    public int getPontuacaoGanha() {
        return this.nivel.getPontuacaoBase() * this.getnRespostasCertas();
    }

    /**
     * Retorna a pontuacao perdida neste nivel fazendo a soma entre o
     * produto do numero de respostas erradas por o valor de cada resposta errada neste nivel
     * e o produto entre o numero de ajudas usadas  nas perguntas do nivel por o valor
     * de cada ajuda usada para este nivel
     *
     * @return
     */
    public int getPontuacaoPerdida() {
        return (this.getnRespostasErradas() * this.nivel.getPontuacaoBaseErrada()) + this.getnAjudasUsadas() * this.nivel.getPontuacaoHint();
    }

}
