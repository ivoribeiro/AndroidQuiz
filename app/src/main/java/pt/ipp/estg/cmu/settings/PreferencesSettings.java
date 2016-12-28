package pt.ipp.estg.cmu.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import pt.ipp.estg.cmu.R;

/**
 * Created by Navega on 12/23/2016.
 */

public class PreferencesSettings {

    private Context mContext;

    public PreferencesSettings(Context context) {
        mContext = context;
    }

    public int getThemePreference() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String theme = SP.getString("list_preference_temas", mContext.getString(R.string.theme_base));


        if (theme.equals(mContext.getString(R.string.theme_base))) {
            return R.style.AppTheme;
        }

        if (theme.equals(mContext.getString(R.string.theme_blue))) {
            return R.style.CustomThemeBlue;
        }

        if (theme.equals(mContext.getString(R.string.theme_pink))) {
            return R.style.CustomThemePink;
        }

        if (theme.equals(mContext.getString(R.string.theme_yellow))) {
            return R.style.CustomThemeYellow;
        }

        return R.style.AppTheme;
    }


    public boolean getSoundPreference() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean sound = SP.getBoolean("checkbox_sound_preference", false);
        return sound;
    }
}
