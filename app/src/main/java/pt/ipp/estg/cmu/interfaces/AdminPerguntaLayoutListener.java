package pt.ipp.estg.cmu.interfaces;


import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;

public interface AdminPerguntaLayoutListener {

    void openNovaPerguntaFragment(Nivel nivel, Pergunta pergunta);

}
