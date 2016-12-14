package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

public class AdminPerguntaActivity extends AppCompatActivity {

    private Nivel mNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pergunta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNivel = getIntent().getParcelableExtra(Util.ARG_LEVEL);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, AdminListaPerguntasFragment.newInstance(mNivel))
                .commit();
    }
}
