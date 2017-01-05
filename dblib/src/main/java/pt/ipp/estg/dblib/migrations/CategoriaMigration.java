package pt.ipp.estg.dblib.migrations;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estg.dblib.R;
import pt.ipp.estg.dblib.Field;
import pt.ipp.estg.dblib.models.Categoria;


public class CategoriaMigration extends Migration {

    public CategoriaMigration(Context context) {
        super(Categoria.TABLE);
        this.addField(new Field(true, true, Categoria.ID, "INTEGER"));
        this.addField(new Field(false, false, Categoria.NOME, "VARCHAR(25)"));
        this.addField(new Field(false, false, Categoria.ATIVA, "NUMERIC"));
        this.addField(new Field(false, false, Categoria.IMAGEM, "VARCHAR(25)"));

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

        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + cinema + "',1,'img_cat_0');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + arte + "',1,'img_cat_1');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + animais + "',1,'img_cat_2');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + monumentos + "',1,'img_cat_3');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + viagens + "',1,'img_cat_4');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + foto + "',1,'img_cat_5');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + tech + "',1,'img_cat_6');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + sport + "',1,'img_cat_7');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + moda + "',1,'img_cat_8');");
        seeder.add("INSERT INTO categoria ('nome','ativa','imagem') VALUES('" + food + "',1,'img_cat_9');");

        this.setSeeders(seeder);
    }
}
