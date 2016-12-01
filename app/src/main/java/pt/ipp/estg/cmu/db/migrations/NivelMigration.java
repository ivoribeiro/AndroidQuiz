package pt.ipp.estg.cmu.db.migrations;


public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.addField(new Field(true, "numero", "VARCHAR(3)"));
        this.addField(new Field(false, "nPerguntas", "NUMERIC"));
        this.addField(new Field(false, "pontuacaoBase", "NUMERIC"));
        this.addField(new Field(false, "pontuacaoBaseErrada", "NUMERIC"));
        this.addField(new Field(false, "pontuacaoHint", "NUMERIC"));
        this.addField(new Field(true, "nRespostasCertas", "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
