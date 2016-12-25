package pt.ipp.estg.cmu.db.migrations;


import java.util.ArrayList;

import pt.ipp.estg.cmu.db.Field;
import pt.ipp.estg.cmu.models.Nivel;

public class NivelMigration extends Migration {

    public NivelMigration() {
        super("nivel");
        this.addField(new Field(true, true, Nivel.ID_NIVEL, "INTEGER"));
        this.addField(new Field(false, false, Nivel.NUMERO, "VARCHAR(20)"));
        this.addField(new Field(false, false, Nivel.CATEGORIA, "VARCHAR(20)"));
        this.addField(new Field(false, false, Nivel.BLOQUEADO, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.PONTUACAO_CERTA, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.PONTUACAO_ERRADA, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.PONTUACAO_DICA, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.N_AJUDAS, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.PONTUACAO, "NUMERIC"));
        this.addField(new Field(false, false, Nivel.N_MIN_RESPOSTAS_CERTAS, "NUMERIC"));

        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());

        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO nivel ('numero'," +
                "'categoria'," +
                "'bloqueado'," +
                "'pontuacaoBase'," +
                "'pontuacaoBaseErrada'," +
                "'pontuacaoHint'," +
                "'nRespostasCertas'," +
                "'nAjudas'," +
                "'pontuacao'," +
                "'nMinRespostasCertas'" +
                ") VALUES('Nivel 1','Tech',0,100,15,5,10,0,0,5);");
        //this.setSeeders(seeder);

    }
}
