package pt.ipp.estg.cmu.db.repositories;


public class PerguntaRepo extends Repo {
    public PerguntaRepo() {
        super("pergunta");
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
}
