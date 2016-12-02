package pt.ipp.estg.cmu.db.migrations;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;

public class JogadorMigration extends Migration {

    public JogadorMigration() {
        super("jogador");
        this.addField(new Field(true,true,"id","INTEGER"));
        this.addField(new Field(false,false,"username","VARCHAR(10)"));
        this.addField(new Field(false,false,"pontuacao","NUMERIC"));
        this.addField(new Field(false,false,"dicas","NUMERIC"));
        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO jogador ('username','pontuacao','dicas') VALUES('ivoribeiro',0,10);");
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
        this.setSeeders(seeder);
    }
}
