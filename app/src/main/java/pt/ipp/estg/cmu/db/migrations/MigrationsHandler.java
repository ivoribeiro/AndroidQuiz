package pt.ipp.estg.cmu.db.migrations;

import java.util.ArrayList;

public class MigrationsHandler {

    private ArrayList<Migration> migrationsList;

    public MigrationsHandler() {

        this.migrationsList = new ArrayList<>();
        this.migrationsList.add(new NivelMigration());
        this.migrationsList.add(new PerguntaMigration());
        this.migrationsList.add(new JogadorMigration());
    }

    public ArrayList<Migration> getMigrationsList() {
        return this.migrationsList;
    }
}
