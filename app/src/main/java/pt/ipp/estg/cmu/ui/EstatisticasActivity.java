package pt.ipp.estg.cmu.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;

public class EstatisticasActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager();
        mViewPager.setOnPageChangeListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.slide_tabs_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        //adapter.addFragment(EstatisticasCategoriaFragment.newInstance());
        //adapter.addFragment(EstatisticasNivelFragment.newInstance());
        //adapter.addFragment(EstatisticasPerguntaFragment.newInstance());

    }

    private void setupViewPager() {
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addFragment(EstatisticasJogoFragment.newInstance());
        adapter.addTitle(getString(R.string.tab_title_estatisticas_jogo));
        //adapter.addFragment(EstatisticasCategoriaFragment.newInstance());
        //adapter.addTitle(getString(R.string.tab_title_estatisticas_categoria));
        //adapter.addFragment(EstatisticasNivelFragment.newInstance());
        //adapter.addTitle(getString(R.string.tab_title_estatisticas_nivel));
        //adapter.addFragment(EstatisticasPerguntaFragment.newInstance());
        //adapter.addTitle(getString(R.string.tab_title_estatisticas_pergunta));

        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
