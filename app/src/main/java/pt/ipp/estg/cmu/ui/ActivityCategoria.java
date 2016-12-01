package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.models.Categoria;

public class ActivityCategoria extends AppCompatActivity {

    private ArrayList<Categoria> mCategorias;
    private CategoriaRepo repo;

    public ActivityCategoria() {
        //TODO onde anda o contexto
        this.repo = new CategoriaRepo(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategorias = getAllCategorias();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, ActivityCategoriaFragment.newInstance(mCategorias))
                .commit();
    }

    private ArrayList<Categoria> getAllCategorias() {
        return this.repo.getAll();
    }

}
