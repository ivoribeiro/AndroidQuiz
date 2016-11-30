package pt.ipp.estg.cmu.DB.Migrations;


public class PerguntaMigration extends Migration {
    public PerguntaMigration() {
        super("pergunta");
        this.addField(new Field(true, "id", "NUMERIC"));
        this.addField(new Field(false, "categoria", "VARCHAR(25)"));
        this.addField(new Field(false, "imagem", "TEXT"));
        this.addField(new Field(false, "respostaCerta", "VARCHAR(15)"));
        this.addField(new Field(true, "nRespostasErradas", "NUMERIC"));
        this.addField(new Field(true, "acertou", "BOOLEAN"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
