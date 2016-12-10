package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.util.Util;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    //data
    private Categoria mCategoria;
    //layout
    private boolean isAdmin;
    private FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategoria = getIntent().getParcelableExtra(Util.ARG_CATEGORIE);
        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        if (isAdmin) {
            mFab.setOnClickListener(this);
        } else {
            mFab.setVisibility(View.GONE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, LevelFragment.newInstance(mCategoria, isAdmin))
                .commit();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            mFab.setVisibility(View.INVISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, AdminNovoNivelFragment.newInstance(mCategoria))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isAdmin) {
            mFab.setVisibility(View.VISIBLE);
            mFab.setImageResource(R.drawable.ic_add);
        }
    }
}
