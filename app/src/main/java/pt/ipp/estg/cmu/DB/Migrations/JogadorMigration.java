package pt.ipp.estg.cmu.DB.Migrations;

public class JogadorMigration extends Migration {

    public JogadorMigration() {
        super("jogador");
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
