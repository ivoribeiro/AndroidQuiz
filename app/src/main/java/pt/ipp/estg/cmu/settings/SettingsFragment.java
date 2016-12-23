package pt.ipp.estg.cmu.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.enums.ThemeEnum;

/**
 * Created by Navega on 12/22/2016.
 */

public class SettingsFragment extends PreferenceFragment {

    private ListPreference mThemeList;
    private PreferencesSettings mPreferences;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = new PreferencesSettings(getActivity());
        // Load the preferences from an XML resource

        addPreferencesFromResource(R.xml.preferences);
        mThemeList = (ListPreference) findPreference("list_preference_temas");
        mThemeList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                saveTheme(o.toString());
                return true;
            }
        });
    }

    private void saveTheme(String tema) {
        String base = getActivity().getResources().getString(R.string.theme_base);
        String blue = getActivity().getResources().getString(R.string.theme_blue);
        String pink = getActivity().getResources().getString(R.string.theme_pink);
        String yellow = getActivity().getResources().getString(R.string.theme_yellow);

        if (tema.equals(base)) {
            mPreferences.saveThemePreference(ThemeEnum.BASE.ordinal());
        }
        if (tema.equals(blue)) {
            mPreferences.saveThemePreference(ThemeEnum.BASE_BLUE.ordinal());
        }
        if (tema.equals(pink)) {
            mPreferences.saveThemePreference(ThemeEnum.BASE_PINK.ordinal());
        }
        if (tema.equals(yellow)) {
            mPreferences.saveThemePreference(ThemeEnum.BASE_YELLOW.ordinal());
        }

    }

    public static String getPreferences(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);

        String tema = SP.getString("list_preference_temas", "1"); // defeito actualiza diariamente

        //PreferencesClass p = new PreferencesClass(wifi, schedule, weather_local, language);
        return tema;
    }

}
