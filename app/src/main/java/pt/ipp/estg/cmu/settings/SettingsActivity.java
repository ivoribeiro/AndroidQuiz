package pt.ipp.estg.cmu.settings;

import android.content.Intent;
import android.os.Bundle;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.SettingsRestartListener;
import pt.ipp.estg.cmu.services.RandQuestionService;
import pt.ipp.estg.cmu.ui.ActivityBase;

public class SettingsActivity extends ActivityBase implements SettingsRestartListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_settings);
        mNavigationView.setCheckedItem(R.id.nav_settings);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, SettingsFragment.newInstance())
                .commit();

    }


    @Override
    public void onThemeRestart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onFreqChange() {
        stopService(new Intent(this, RandQuestionService.class));
        startService(new Intent(this, RandQuestionService.class));
    }
}
