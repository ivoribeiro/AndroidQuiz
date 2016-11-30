package pt.ipp.estg.cmu.DB.Migrations;


import pt.ipp.estg.cmu.DB.Migration;

/**
 * Created by ivoribeiro on 29-11-2016.
 */

public class PerguntaMigration extends Migration {
    public PerguntaMigration() {
        super("pergunta");
        this.setUpgrade("DROP TABLE IF EXISTS pergunta");
    }
}
