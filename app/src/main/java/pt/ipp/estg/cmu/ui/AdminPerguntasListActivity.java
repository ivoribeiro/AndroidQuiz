package pt.ipp.estg.cmu.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminPerguntaAdapterChangeListener;
import pt.ipp.estg.cmu.interfaces.AdminPerguntaLayoutListener;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;

/**
 * @author 8130031
 * @author 8130258
 */
public class AdminPerguntasListActivity extends AppCompatActivity implements
        AdminPerguntaLayoutListener,
        AdminPerguntaAdapterChangeListener {

    private Nivel mNivel;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_admin_pergunta);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setSubtitle(getResources().getString(R.string.admin_toolbar_subtitle));

        mNivel = getIntent().getParcelableExtra(Util.ARG_LEVEL);

        if (null == savedInstanceState) {
            startPerguntasListFragment();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void openNovaPerguntaFragment(Nivel nivel, Pergunta pergunta) {
        boolean isTablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
        boolean isLandScape = getResources().getBoolean(R.bool.is_landscape);


        if (isTablet && isLandScape) {
            //tablet and landscape
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_detail, AdminNovaPerguntaFragment.newInstance(nivel, pergunta, isLandScape, isTablet))
                    .addToBackStack(Util.STACK_ADMIN)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, AdminNovaPerguntaFragment.newInstance(nivel, pergunta, isLandScape, isTablet))
                    .addToBackStack(Util.STACK_ADMIN)
                    .commit();
        }
    }

    @Override
    public void onPerguntaSave() {
        startPerguntasListFragment();
    }

    private void startPerguntasListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, AdminPerguntasListFragment.newInstance(mNivel))
                .commit();
    }


}
