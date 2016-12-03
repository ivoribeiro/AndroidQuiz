package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
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
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        if (isAdmin) {
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        } else {
            mFab.setVisibility(View.GONE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }
}
