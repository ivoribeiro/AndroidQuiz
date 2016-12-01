package pt.ipp.estg.cmu.db.migrations;


public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.addField(new Field(true, true, "id", "NUMERIC"));
        this.addField(new Field(false, false, "numero", "VARCHAR(3)"));
        this.addField(new Field(false, false, "nPerguntas", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBase", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoBaseErrada", "NUMERIC"));
        this.addField(new Field(false, false, "pontuacaoHint", "NUMERIC"));
        this.addField(new Field(true, false, "nRespostasCertas", "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
