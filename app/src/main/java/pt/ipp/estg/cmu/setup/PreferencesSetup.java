package pt.ipp.estg.cmu.setup;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Navega on 12/21/2016.
 */

public class PreferencesSetup {

    private static final String USER_FLAGS_PREFERENCE = "user_flags";
    private static final String USER_FLAG_SETUP = "flag_finish_setup";
    private static final String USER_FLAG_AVATAR = "flag_chosen_avatar";
    private static final String USER_FLAG_NICKNAME = "flag_chosen_nickname";
    private static final String USER_FLAG_PIN = "flag_chosen_pin";

    private Context mContext;

    public PreferencesSetup(Context mContext) {
        this.mContext = mContext;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////SETUP

    /**
     * Guarda se o setup da app foi concluido
     *
     * @param flag true- setup concluid false setup nao concluido
     */
    protected void saveFlagSetupPreference(boolean flag) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_FLAG_SETUP, flag);
        editor.commit();
    }

    /**
     * Retorna se o setup da app foi concluido
     *
     * @return true setup concluido, false nao concluido
     */
    public boolean getFlagSetupPreference() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean(USER_FLAG_SETUP, false);
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////AVATAR

    /**
     * Guarda o avatar escolhido
     *
     * @param
     */
    protected void saveFlagAvatarPreference(int flag) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_FLAG_AVATAR, flag);
        editor.commit();
    }

    /**
     * Retorna o avatar escolhido
     *
     * @return
     */
    public int getFlagAvatarPreference() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_FLAG_AVATAR, 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////NICKNAME

    /**
     * Guarda o nickname escolhido
     *
     * @param
     */
    public void saveFlagNickNamePreference(String flag) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FLAG_NICKNAME, flag);
        editor.commit();
    }

    /**
     * Retorna o nickname escolhido
     *
     * @return
     */
    public String getFlagNickNamePreference() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_FLAGS_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_FLAG_NICKNAME, "");
    }

}
