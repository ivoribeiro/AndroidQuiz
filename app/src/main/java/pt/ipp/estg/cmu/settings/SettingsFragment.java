package pt.ipp.estg.cmu.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.SettingsRestartListener;

/**
 * @author 8130031
 * @author 8130258
 */
public class SettingsFragment extends PreferenceFragment {

    private ListPreference mThemeList;
    private ListPreference mFreqList;
    private SettingsRestartListener mListener;


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

        mThemeList = (ListPreference) findPreference("list_preference_temas");
        mThemeList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                mListener.onThemeRestart();
                return true;
            }
        });

        mFreqList = (ListPreference) findPreference("list_preference_frequencia");
        mFreqList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                mListener.onFreqChange();
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (SettingsRestartListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (SettingsRestartListener) activity;
    }
}
