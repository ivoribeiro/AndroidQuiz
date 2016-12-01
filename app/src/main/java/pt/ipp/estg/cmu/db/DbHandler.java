package pt.ipp.estg.cmu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import pt.ipp.estg.cmu.db.migrations.MigrationsHandler;


public class DbHandler extends SQLiteOpenHelper {

    /**
     * Class responsavel por criar a base de dados
     */

    private static final int DATABASE_VERSION = 1;
    private ArrayList<pt.ipp.estg.cmu.db.migrations.Migration> migrations;

    public DbHandler(Context context, String db) {
        super(context, db, null, DATABASE_VERSION);
        MigrationsHandler mh = new MigrationsHandler();
        this.migrations = mh.getMigrationsList();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < this.migrations.size(); i++) {
            db.execSQL(this.migrations.get(i).getCreate());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            for (int i = 0; i < this.migrations.size(); i++) {
                db.execSQL(this.migrations.get(i).getUpgrade());
            }
            System.out.println("Database Actualizada");
            this.onCreate(db);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
