package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.settings.PreferencesSettings;

public class EstatisticasActivity extends ActivityBase implements ViewPager.OnPageChangeListener {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_estatisticas);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager();
        mViewPager.setOnPageChangeListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.slide_tabs_layout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager() {
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addFragment(EstatisticasGraphs.newInstance());
        adapter.addTitle(getString(R.string.tab_title_estatisticas_jogo));

        adapter.addFragment(EstatisticasJogoFragment.newInstance());
        adapter.addTitle(getString(R.string.tab_title_estatisticas_jogo));
        adapter.addFragment(EstatisticasCategoriaFragment.newInstance());
        adapter.addTitle(getString(R.string.tab_title_estatisticas_categoria));
        adapter.addFragment(EstatisticasNivelFragment.newInstance());
        adapter.addTitle(getString(R.string.tab_title_estatisticas_nivel));
        //adapter.addFragment(EstatisticasPerguntaFragment.newInstance());
        //adapter.addTitle(getString(R.string.tab_title_estatisticas_pergunta));

        mViewPager.setAdapter(adapter);
    }

    @Override
    protected boolean useToolbar() {
        return false;
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
