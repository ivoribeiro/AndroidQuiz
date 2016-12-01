package pt.ipp.estg.cmu.db.repositories;


import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Nivel;

public class NivelRepo extends Repo implements RepositoryInterface<Nivel> {

    public NivelRepo(Context context) {
        super(context, "nivel");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("NUMERO", "numero");
        this.addField("CATEGORIA", "categoria");
        this.addField("N_PERGUNTAS", "nPerguntas");
        this.addField("PONTUACAO_CERTA", "pontuacaoBase");
        this.addField("PONTUACAO_ERRADA", "pontuacaoBaseErrada");
        this.addField("PONTUACAO_DICA", "pontuacaoHint");
        this.addField("N_RESPOSTAS_CERTAS", "nRespostasCertas");

    }

    @Override
    public ArrayList<Nivel> getAll() {
        return null;
    }

    @Override
    public Nivel getById(int id) {
        return null;
    }

    @Override
    public Nivel insertInto(Nivel nivel) {
        return null;
    }
}
