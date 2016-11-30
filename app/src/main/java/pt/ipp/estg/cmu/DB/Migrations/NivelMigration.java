package pt.ipp.estg.cmu.DB.Migrations;

import pt.ipp.estg.cmu.DB.Migration;

/**
 * Created by ivoribeiro on 29-11-2016.
 */

public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.setCreate("CREATE TABLE nivel(id PRIMARY KEY)");
        this.setUpgrade("DROP TABLE IF EXISTS nivel");
    }
}
