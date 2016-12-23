package pt.ipp.estg.cmu.ui;

import android.os.Bundle;

import pt.ipp.estg.cmu.R;

/**
 * Created by Navega on 12/22/2016.
 */

public class SettingsActivity extends ActivityBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mNavigationView.setCheckedItem(R.id.nav_settings);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, SettingsFragment.newInstance())
                .commit();
    }
}
