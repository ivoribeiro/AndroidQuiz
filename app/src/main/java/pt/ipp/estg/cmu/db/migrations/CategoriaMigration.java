package pt.ipp.estg.cmu.db.migrations;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;


public class CategoriaMigration extends Migration {

    public CategoriaMigration() {
        super("categoria");
        this.addField(new Field(true,true,"id","INTEGER"));
        this.addField(new Field(false,false,"nome","VARCHAR(25)"));
        String create=this.generateCreate();
        this.setCreate(create);
        this.setUpgrade(this.generateUpgrade());
        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO categoria ('nome') VALUES('Tech');");
        seeder.add("INSERT INTO categoria ('nome') VALUES('História');");
        seeder.add("INSERT INTO categoria ('nome') VALUES('Geografia');");
        seeder.add("INSERT INTO categoria ('nome') VALUES('Desporto');");
        this.setSeeders(seeder);
    }
}
