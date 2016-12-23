package pt.ipp.estg.cmu.settings;

import android.content.Context;
import android.content.SharedPreferences;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.enums.ThemeEnum;

/**
 * Created by Navega on 12/23/2016.
 */

public class PreferencesSettings {

    private static final String SETTINGS_PREFERENCE = "user_flags_settings";
    private static final String USER_FLAG_THEME = "flag_theme";

    private Context mContext;

    public PreferencesSettings(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Guarda o nickname escolhido
     *
     * @param
     */
    public void saveThemePreference(int flag) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt(USER_FLAG_THEME, flag);
        editor.commit();
    }

    /**
     * Retorna o nickname escolhido
     *
     * @return
     */
    public int getThemePreference() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(USER_FLAG_THEME, 0);

        if (theme == ThemeEnum.BASE.ordinal()) {
            return R.style.AppTheme;
        }

        if (theme == ThemeEnum.BASE_BLUE.ordinal()) {
            return R.style.CustomThemeBlue;
        }

        if (theme == ThemeEnum.BASE_PINK.ordinal()) {
            return R.style.CustomThemePink;
        }

        if (theme == ThemeEnum.BASE_YELLOW.ordinal()) {
            return R.style.CustomThemeYellow;
        }

        return R.style.AppTheme;
    }
}
