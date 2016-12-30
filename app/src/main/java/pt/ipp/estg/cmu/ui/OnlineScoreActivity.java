package pt.ipp.estg.cmu.ui;

import android.os.Bundle;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.settings.PreferencesSettings;

/**
 * Created by Navega on 12/30/2016.
 */

public class OnlineScoreActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_online_score);


    }
}
