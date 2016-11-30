package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Nivel;

public class ActivityLevel extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,ActivityLevelFragment.newInstance(getAllNiveis())).commit();
    }


    private ArrayList<Nivel>  getAllNiveis(){
        ArrayList<Nivel> mNiveis = new ArrayList<>();
        mNiveis.add(new Nivel("1", 10, 1, 1, 1, false));
        mNiveis.add(new Nivel("2", 10, 0, 1, 1, true));
        mNiveis.add(new Nivel("3", 10, 1, 1, 1, true));
        mNiveis.add(new Nivel("4", 10, 0, 1, 1, true));
        mNiveis.add(new Nivel("5", 10, 1, 1, 1, true));
        mNiveis.add(new Nivel("6", 10, 0, 1, 1, true));
        mNiveis.add(new Nivel("7", 10, 0, 1, 1, true));
        mNiveis.add(new Nivel("8", 10, 0, 1, 1, true));
        return mNiveis;
    }

}
