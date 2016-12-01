package pt.ipp.estg.cmu.db.repositories;


public class NivelRepo extends Repo {

    public NivelRepo() {
        super("nivel");
        this.setFields();
    }

    private void setFields(){
        this.addField("ID","id");
        this.addField("NUMERO","numero");
        this.addField("N_PERGUNTAS","nPerguntas");
        this.addField("PONTUACAO_CERTA","pontuacaoBase");
        this.addField("PONTUACAO_ERRADA","pontuacaoBaseErrada");
        this.addField("PONTUACAO_DICA","pontuacaoHint");
        this.addField("N_RESPOSTAS_CERTAS","nRespostasCertas");

    }
}
