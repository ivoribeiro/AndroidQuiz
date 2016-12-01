package pt.ipp.estg.cmu.db.repositories;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Jogador;

/**
 * Created by ivoribeiro on 01-12-2016.
 */

public class JogadorRepo extends Repo implements RepositoryInterface<Jogador> {

    public JogadorRepo(Context context) {
        super(context, "jogador");
        this.setFields();
    }

    private void setFields() {
        this.addField("USER", "username");
        this.addField("PONTUACAO", "pontuacao");
        this.addField("DICAS", "dicas");
    }

    @Override
    public ArrayList<Jogador> getAll() {
        return null;
    }

    @Override
    public Jogador getById(int id) {
        return null;
    }
}
