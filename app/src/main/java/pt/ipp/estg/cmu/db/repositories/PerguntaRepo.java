package pt.ipp.estg.cmu.db.repositories;


import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Pergunta;

public class PerguntaRepo extends Repo implements RepositoryInterface<Pergunta> {
    public PerguntaRepo(Context context) {
        super(context, "pergunta");
        this.setFields();
    }

    private void setFields() {
        this.addField("ID", "id");
        this.addField("CATEGORIA", "categoria");
        this.addField("IMAGEM", "imagem");
        this.addField("RESPOSTA", "respostaCerta");
        this.addField("RESPOSTAS_ERRADAS", "nRespostasErradas");
        this.addField("ACERTOU", "acertou");
    }

    @Override
    public ArrayList<Pergunta> getAll() {
        return null;
    }

    @Override
    public Pergunta getById(int id) {
        return null;
    }

    @Override
    public Pergunta insertInto(Pergunta pergunta) {
        return null;
    }
}
