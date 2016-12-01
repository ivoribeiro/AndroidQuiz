package pt.ipp.estg.cmu.db.migrations;


import pt.ipp.estg.cmu.db.Field;

public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.addField(new Field(false, true, "id", "NUMERIC"));
        this.addField(new Field(true, false, "numero", "VARCHAR(3)"));
        this.addField(new Field(false, false, "nPerguntas", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBase", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBaseErrada", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoHint", "NUMERIC"));
        this.addField(new Field(true, false, "nRespostasCertas", "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
