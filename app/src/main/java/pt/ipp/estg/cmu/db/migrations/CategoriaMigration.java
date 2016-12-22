package pt.ipp.estg.cmu.db.migrations;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.Field;


public class CategoriaMigration extends Migration {

    public CategoriaMigration(Context context) {
        super("categoria");
        this.addField(new Field(true, true, "id", "INTEGER"));
        this.addField(new Field(false, false, "nome", "VARCHAR(25)"));
        this.addField(new Field(false, false, "ativa", "NUMERIC"));
        String create = this.generateCreate();
        this.setCreate(create);
        this.setUpgrade(this.generateUpgrade());
        ArrayList<String> seeder = new ArrayList<>();

        String cinema = context.getResources().getString(R.string.cat_0);
        String arte = context.getResources().getString(R.string.cat_1);
        String animais = context.getResources().getString(R.string.cat_2);
        String monumentos = context.getResources().getString(R.string.cat_3);
        String viagens = context.getResources().getString(R.string.cat_4);
        String foto = context.getResources().getString(R.string.cat_5);
        String tech = context.getResources().getString(R.string.cat_6);
        String sport = context.getResources().getString(R.string.cat_7);
        String moda = context.getResources().getString(R.string.cat_8);
        String food = context.getResources().getString(R.string.cat_9);

        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + cinema + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + arte + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + animais + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + monumentos + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + viagens + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + foto + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + tech + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + sport + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + moda + "',1);");
        seeder.add("INSERT INTO categoria ('nome','ativa') VALUES('" + food + "',1);");

        this.setSeeders(seeder);
    }
}
