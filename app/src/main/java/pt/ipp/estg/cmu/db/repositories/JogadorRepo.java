package pt.ipp.estg.cmu.db.repositories;

/**
 * Created by ivoribeiro on 01-12-2016.
 */

public class JogadorRepo extends Repo implements RepositoryInterface {

    public JogadorRepo() {
        super("jogador");
        this.setFields();
    }

    private void setFields(){
        this.addField("USER","username");
        this.addField("PONTUACAO","pontuacao");
        this.addField("DICAS","dicas");
    }
}
