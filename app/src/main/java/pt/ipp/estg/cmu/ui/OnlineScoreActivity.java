package pt.ipp.estg.cmu.ui;

import android.os.Bundle;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.settings.PreferencesSettings;

/**
 * @author 8130031
 * @author 8130258
 */
public class OnlineScoreActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_online_score);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, OnlineScoreFragment.newInstance()).commit();
    }
}
