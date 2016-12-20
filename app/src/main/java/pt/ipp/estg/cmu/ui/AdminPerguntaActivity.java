package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

public class AdminPerguntaActivity extends AppCompatActivity {

    private Nivel mNivel;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pergunta);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mNivel = getIntent().getParcelableExtra(Util.ARG_LEVEL);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, AdminListaPerguntasFragment.newInstance(mNivel))
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
