package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.models.Categoria;

public class ActivityCategoria extends AppCompatActivity {

    private ArrayList<Categoria> mCategorias;
    private CategoriaRepo repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.repo = new CategoriaRepo(this);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategorias = getAllCategorias();
        //this.repo.insertInto(new Categoria("Tech"));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, ActivityCategoriaFragment.newInstance(mCategorias))
                .commit();
    }

    private ArrayList<Categoria> getAllCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Tech"));
        return categorias;
        //return this.repo.getAll();
    }

}
