package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;

import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;

public class EstatisticasNivel {

    private Context mContext;
    private NivelRepo mNivelRepo;
    private PerguntaRepo mPerguntaRepo;
    private Nivel nivel;

    public EstatisticasNivel(Context context, int nivel) {
        this.mContext = context;
        this.mNivelRepo = new NivelRepo(context);
        this.nivel = mNivelRepo.getById(nivel);
    }

    public EstatisticasNivel(Context context) {
        this.mContext = context;
        this.mNivelRepo = new NivelRepo(context);
        this.mPerguntaRepo = new PerguntaRepo(context);
    }

    /**
     * Retorna o numero de perguntas do nivel
     *
     * @return
     */
    public int getnPerguntas() {
        return nivel.getnPerguntas();
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
     * Retorna o numero de respostas erradas ás perguntas do nivel
     *
     * @return
     */
    public int getnRespostasErradas() {
        return this.mPerguntaRepo.getSumNivelErradas(nivel.getId());
    }

    /**
     * Retorna o numero de respostas certas ás perguntas do nivel
     *
     * @return
     */
    public int getnRespostasCertas() {
        return this.nivel.getnRespostasCertas();
    }

    /**
     * Retorna o numero de ajudas usadas ás perguntas do nivel
     *
     * @return
     */
    public int getnAjudasUsadas() {
        //TODO
        return 0;
    }

    /**
     * Retorna toda a pontuacao ganha neste nivel
     *
     * @return
     */
    public int getPontuacaoGanha() {
        return this.nivel.getPontuacaoBase() * this.nivel.getnRespostasCertas();
    }

    /**
     * Retorna o numero de pontuacao perdida para este nivel
     * @return
     */
    public int getPontuacaoPerdida() {
        //TODO subtrair ajudas
        return this.getnRespostasErradas() * this.nivel.getPontuacaoBaseErrada();
    }

}
