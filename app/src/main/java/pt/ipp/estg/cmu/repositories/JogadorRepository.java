package pt.ipp.estg.cmu.repositories;

import android.database.Observable;

import java.util.List;

import pt.ipp.estg.cmu.models.Jogador;

/**
 * Interface that represents a Repository for getting {@link Jogador} related data.
 */
public interface JogadorRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Jogador}.
     */
    Observable<List<Jogador>> jogadores();

    /**
     * Get an {@link Observable} which will emit a {@link Jogador}.
     *
     * @param username The username used to retrieve user data.
     */
    Observable<Jogador> jogador(final String username);
}