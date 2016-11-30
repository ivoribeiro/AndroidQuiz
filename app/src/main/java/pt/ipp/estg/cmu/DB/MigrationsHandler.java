package pt.ipp.estg.cmu.DB;

import java.util.ArrayList;

import pt.ipp.estg.cmu.DB.Migrations.*;

/**
 * Created by ivoribeiro on 29-11-2016.
 */

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
