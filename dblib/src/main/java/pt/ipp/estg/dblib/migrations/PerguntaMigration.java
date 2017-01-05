package pt.ipp.estg.dblib.migrations;


import java.util.ArrayList;

import pt.ipp.estg.dblib.Field;
import pt.ipp.estg.dblib.models.Pergunta;

public class PerguntaMigration extends Migration {

    public PerguntaMigration() {
        super("pergunta");
        this.addField(new Field(true, true, Pergunta.ID_PERGUNTA, "INTEGER"));
        this.addField(new Field(false, false, Pergunta.NIVEL, "NUMERIC"));
        this.addField(new Field(false, false, Pergunta.IMAGEM, "TEXT"));
        this.addField(new Field(false, false, Pergunta.RESPOSTA, "VARCHAR(15)"));
        this.addField(new Field(false, false, Pergunta.RESPOSTAS_ERRADAS, "NUMERIC"));
        this.addField(new Field(false, false, Pergunta.ACERTOU, "NUMERIC"));
        this.addField(new Field(false, false, Pergunta.STRING_ALEATORIA, "VARCHAR(15)"));
        this.addField(new Field(false, false, Pergunta.RESPOSTA_ACTUAL, "VARCHAR(15)"));
        this.addField(new Field(false, false, Pergunta.N_AJUDAS_USADAS, "NUMERIC"));
        this.setCreate(this.generateCreate());
        this.setUpgrade(this.generateUpgrade());

        ArrayList<String> seeder = new ArrayList<>();
        seeder.add("INSERT INTO pergunta ('nivel'," +
                "'imagem'," +
                "'respostaCerta'," +
                "'nRespostasErradas'," +
                "'acertou'," +
                "'stringAleatoria'" +
                "'respostaActual'" +
                "'nAjudasUsadas'" +
                ") VALUES(1,'CMU/ny.jpg','NEW YORK',0,0,'NAEBWCYDOERFKG','',0);");
        //this.setSeeders(seeder);
    }
}
