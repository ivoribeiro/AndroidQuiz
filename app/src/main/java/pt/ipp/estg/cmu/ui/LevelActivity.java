package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

public class LevelActivity extends AppCompatActivity {

    private Categoria mCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategoria = getIntent().getParcelableExtra(Util.ARG_CATEGORIE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, LevelFragment.newInstance( mCategoria))
                .commit();
    }
}
