package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pt.ipp.estg.cmu.R;

/**
 * Created by Navega on 12/22/2016.
 */

public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
