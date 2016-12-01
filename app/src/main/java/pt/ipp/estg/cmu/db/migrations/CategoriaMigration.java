package pt.ipp.estg.cmu.db.migrations;

import pt.ipp.estg.cmu.db.Field;

/**
 * Created by ivoribeiro on 01-12-2016.
 */

public class CategoriaMigration extends Migration {

    public CategoriaMigration() {
        super("categoria");
        this.addField(new Field(true,true,"id","NUMERIC"));
        this.addField(new Field(false,false,"nome","VARCHAR(25)"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
