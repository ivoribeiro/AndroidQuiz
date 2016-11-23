package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;

public class ActivityQuestion extends AppCompatActivity implements ClickQuestionListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CATEGORIA X");
        toolbar.setSubtitle("NIVEL Y");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addFragment(ActivityQuestionFragment.newInstance(0, "NEW YORK", "NEWYORK"));
        adapter.addFragment(ActivityQuestionFragment.newInstance(1, "EVORA", "EVORA"));

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void setCorrectAnswer() {
        System.out.println("ACABOU");
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }


}
