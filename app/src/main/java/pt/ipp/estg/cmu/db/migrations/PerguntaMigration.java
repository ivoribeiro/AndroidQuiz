package pt.ipp.estg.cmu.db.migrations;


import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;

public class PerguntaMigration extends Migration {

    public PerguntaMigration() {
        super("pergunta");
        this.addField(new Field(true, true, "id", "INTEGER"));
        this.addField(new Field(false, false, "nivel", "NUMERIC"));
        this.addField(new Field(false, false, "imagem", "TEXT"));
        this.addField(new Field(false, false, "respostaCerta", "VARCHAR(15)"));
        this.addField(new Field(false, false, "nRespostasErradas", "NUMERIC"));
        this.addField(new Field(false, false, "acertou", "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());

        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO pergunta ('nivel'," +
                "'imagem'," +
                "'respostaCerta'," +
                "'nRespostasErradas'," +
                "'acertou'" +
                ") VALUES(1,'caminho/imagem','NEW YORK',0,0);");
        this.setSeeders(seeder);
    }
}
