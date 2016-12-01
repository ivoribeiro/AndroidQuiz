package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

public class ActivityLevel extends AppCompatActivity {

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
                .add(R.id.frame_layout, ActivityLevelFragment.newInstance(getAllNiveis(), mCategoria))
                .commit();
    }

    private ArrayList<Nivel> getAllNiveis() {
        ArrayList<Nivel> mNiveis = new ArrayList<>();
        mNiveis.add(new Nivel("1", 10, 1, 1, 1, false, "x"));
        mNiveis.add(new Nivel("2", 10, 0, 1, 1, true, "x"));
        mNiveis.add(new Nivel("3", 10, 1, 1, 1, true, "x"));
        mNiveis.add(new Nivel("4", 10, 0, 1, 1, true, "x"));
        mNiveis.add(new Nivel("5", 10, 1, 1, 1, true, "x"));
        mNiveis.add(new Nivel("6", 10, 0, 1, 1, true, "x"));
        mNiveis.add(new Nivel("7", 10, 0, 1, 1, true, "x"));
        mNiveis.add(new Nivel("8", 10, 0, 1, 1, true, "x"));
        return mNiveis;
    }
}
