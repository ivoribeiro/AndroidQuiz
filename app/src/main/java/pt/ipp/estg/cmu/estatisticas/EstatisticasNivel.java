package pt.ipp.estg.cmu.estatisticas;

import android.content.Context;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Nivel;

public class EstatisticasNivel {

    private Context mContext;
    private NivelRepo mNivelRepo;
    private Nivel nivel;

    public EstatisticasNivel(Context context,int nivel) {
        this.mContext = context;
        this.mNivelRepo = new NivelRepo(context);
        this.nivel= mNivelRepo.getById(nivel);
    }

    /**
     * Retorna o numero de perguntas do nivel
     * @return
     */
    public int getnPerguntas() {
        return nivel.getnPerguntas();
    }

    /**
     * Retorna a pontuacao acumulada do nivel
     * @return
     */
    public int getPontuacao() {
        return this.nivel.getPontuacao();
    }

    /**
     * Retorna o numero de respostas erradas ás perguntas do nivel
     * @return
     */
    public int getnRespostasErradas() {
        //TODO somar numero de respostas erradas de cada pergunta do nivel
        return 0;
    }

    /**
     * Retorna o numero de respostas certas ás perguntas do nivel
     * @return
     */
    public int getnRespostasCertas() {
        return this.nivel.getnRespostasCertas();
    }

    /**
     * Retorna o numero de ajudas usadas ás perguntas do nivel
     * @return
     */
    public int getnAjudasUsadas() {
        return 0;
    }

}
