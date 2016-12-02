package pt.ipp.estg.cmu.db.migrations;


import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;

public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.addField(new Field(true, true, "id", "INTEGER"));
        this.addField(new Field(false, false, "numero", "VARCHAR(3)"));
        this.addField(new Field(false, false, "categoria", "VARCHAR(20)"));
        this.addField(new Field(false, false, "bloqueado", "NUMERIC"));
        this.addField(new Field(false, false, "nPerguntas", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBase", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBaseErrada", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoHint", "NUMERIC"));
        this.addField(new Field(false, false, "nRespostasCertas", "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());

        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO nivel ('numero'," +
                "'categoria'," +
                "'bloqueado'," +
                "'nPerguntas'," +
                "'pontuacaoBase'," +
                "'pontuacaoBaseErrada'," +
                "'pontuacaoHint'," +
                "'nRespostasCertas'" +
                ") VALUES('Nivel 1','Tech',0,10,10,2,2,0);");
        this.setSeeders(seeder);

    }
}
