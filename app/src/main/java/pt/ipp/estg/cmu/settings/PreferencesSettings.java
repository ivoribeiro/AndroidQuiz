package pt.ipp.estg.cmu.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.util.Arrays;

import pt.ipp.estg.cmu.R;

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

    public int getFrequenciaUpdate() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String[] info = mContext.getResources().getStringArray(R.array.frequencias);
        String frequencia = SP.getString("list_preference_frequencia", info[0]);
        int index = Arrays.asList(info).indexOf(frequencia);

        switch (index) {
            case 0:
                return 1;
            case 1:
                return 10;
            case 2:
                return 30;
            case 3:
                return 60;
            case 4:
                return 120;
        }
        return index;
    }

    public boolean wantNotifications() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean("notification_checkbox_preference", false);
    }

    public boolean wantVibration() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean("notifications_vibrate", false);
    }

    public Ringtone wantRingTone() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String alarms = preferences.getString("notifications_ringtone", "default ringtone");
        Uri uri = Uri.parse(alarms);
        Ringtone r = RingtoneManager.getRingtone(mContext, uri);
        return r;
    }
}
