package pt.ipp.estg.cmu.db.migrations;

import pt.ipp.estg.cmu.db.Field;

public class JogadorMigration extends Migration {

    public JogadorMigration() {
        super("jogador");
        this.addField(new Field(true,false,"username","VARCHAR(10)"));
        this.addField(new Field(true,false,"pontuacao","NUMERIC"));
        this.addField(new Field(true,false,"dicas","NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
