package pt.ipp.estg.cmu.DB.Migrations;


public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());
    }
}
