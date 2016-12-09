package pt.ipp.estg.cmu.db.migrations;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;


public class CategoriaMigration extends Migration {

    public CategoriaMigration() {
        super("categoria");
        this.addField(new Field(true, true, "id", "INTEGER"));
        this.addField(new Field(false, false, "nome", "VARCHAR(25)"));
        this.addField(new Field(false, false, "ativa", "NUMERIC"));
        String create = this.generateCreate();
        this.setCreate(create);
        this.setUpgrade(this.generateUpgrade());
        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('Tech',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('História',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('Geografia',0);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('Desporto',0);");
        this.setSeeders(seeder);
    }
}
