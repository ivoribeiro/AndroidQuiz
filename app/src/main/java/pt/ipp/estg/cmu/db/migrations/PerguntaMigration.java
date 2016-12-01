package pt.ipp.estg.cmu.db.migrations;


import pt.ipp.estg.cmu.db.Field;

public class PerguntaMigration extends Migration {

    public PerguntaMigration() {
        super("pergunta");
        this.addField(new Field(true, true, "id", "NUMERIC"));
        this.addField(new Field(false, false, "categoria", "VARCHAR(25)"));
        this.addField(new Field(false, false, "imagem", "TEXT"));
        this.addField(new Field(false, false, "respostaCerta", "VARCHAR(15)"));
        this.addField(new Field(true, false, "nRespostasErradas", "NUMERIC"));
        this.addField(new Field(true, false, "acertou", "BOOLEAN"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
