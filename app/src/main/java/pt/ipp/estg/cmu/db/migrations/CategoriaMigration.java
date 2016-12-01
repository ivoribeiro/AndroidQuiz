package pt.ipp.estg.cmu.db.migrations;

import pt.ipp.estg.cmu.db.Field;

/**
 * Created by ivoribeiro on 01-12-2016.
 */

public class CategoriaMigration extends Migration {

    public CategoriaMigration() {
        super("categoria");
        this.addField(new Field(true,true,"id","INTEGER"));
        this.addField(new Field(false,false,"nome","VARCHAR(25)"));
        String create=this.generateCreate();
        this.setCreate(create);
        this.setUpgrade(this.generateUpgrade());
    }
}
