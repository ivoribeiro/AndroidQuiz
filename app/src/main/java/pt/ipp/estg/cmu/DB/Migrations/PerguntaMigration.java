package pt.ipp.estg.cmu.DB.Migrations;


public class PerguntaMigration extends Migration {
    public PerguntaMigration() {
        super("pergunta");
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
