package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends AppCompatActivity {

    //layout
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }
}
