package pt.ipp.estg.cmu.interfaces;


import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;

/**
 * @author 8130031
 * @author 8130258
 */
public interface AdminPerguntaLayoutListener {

    void openNovaPerguntaFragment(Nivel nivel, Pergunta pergunta);

}
