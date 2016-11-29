package pt.ipp.estg.cmu.repositories;

import android.database.Observable;

import java.util.List;

import pt.ipp.estg.cmu.models.Jogador;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;

/**
 * Created by ivoribeiro on 29-11-2016.
 */

public interface NivelRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link Nivel}.
     */
    Observable<List<Nivel>> niveis();

    /**
     * Get an {@link Observable} which will emit a {@link Nivel}.
     *
     * @param numero The username used to retrieve user data.
     */
    Observable<Nivel> nivel(final String numero);
}
