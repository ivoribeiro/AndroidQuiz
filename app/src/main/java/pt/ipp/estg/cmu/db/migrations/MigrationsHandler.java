package pt.ipp.estg.cmu.db.migrations;

import android.content.Context;

import java.util.ArrayList;

public class MigrationsHandler {

    private ArrayList<Migration> migrationsList;

    public MigrationsHandler(Context context) {

        this.migrationsList = new ArrayList<>();
        this.migrationsList.add(new NivelMigration());
        this.migrationsList.add(new PerguntaMigration());
        this.migrationsList.add(new CategoriaMigration(context));
    }

    public ArrayList<Migration> getMigrationsList() {
        return this.migrationsList;
    }
}
