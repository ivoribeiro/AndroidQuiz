package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.logging.Level;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

public class ActivityLevel extends AppCompatActivity {

    private Categoria mCategoria;
    private NivelRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.repo = new NivelRepo(this);
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
        return this.repo.getAllByCategoria(this.mCategoria.getNome());
    }
}
