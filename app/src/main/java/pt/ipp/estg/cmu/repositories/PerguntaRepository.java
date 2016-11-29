package pt.ipp.estg.cmu.repositories;

import android.database.Observable;

import java.util.List;

import pt.ipp.estg.cmu.models.Jogador;
import pt.ipp.estg.cmu.models.Pergunta;

public interface PerguntaRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link Jogador}.
     */
    Observable<List<Pergunta>> perguntas();

    /**
     * Get an {@link Observable} which will emit a {@link Pergunta}.
     *
     * @param id The username used to retrieve user data.
     */
    Observable<Pergunta> pergunta(final String id);
}
