package pt.ipp.estg.cmu.DB.Migrations;

import pt.ipp.estg.cmu.DB.Migration;

/**
 * Created by ivoribeiro on 29-11-2016.
 */

public class JogadorMigration extends Migration {

    public JogadorMigration() {
        super("jogador");
        this.setCreate("CREATE TABLE jogador(username PRIMARY KEY)");
        this.setUpgrade("DROP TABLE IF EXISTS jogador");
    }
}
