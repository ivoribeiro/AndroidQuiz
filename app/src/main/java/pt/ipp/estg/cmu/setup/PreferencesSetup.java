package pt.ipp.estg.cmu.setup;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesSetup {

    private static final String USER_FLAGS_PREFERENCE = "user_flags";
    private static final String USER_FLAG_SETUP = "flag_finish_setup";
    private static final String USER_FLAG_AVATAR = "flag_chosen_avatar";
    private static final String USER_FLAG_NICKNAME = "flag_chosen_nickname";
    private static final String USER_FLAG_PIN = "flag_chosen_pin";
    private static final String WIDGET_INDEX_PREFERENCE = "widget_index_preference";
    private static final String WIDGET_ATIVO_PREFERENCE = "widget_ativo_preference";
    private static final String WIDGET_RESPOSTA_PREFERENCE = "widget_resposta_preference";


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

    /**
     * Guarda o index actual da resposta no widget
     *
     * @param index
     */
    public void saveIndexRespostaWidget(int index) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_INDEX_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(WIDGET_INDEX_PREFERENCE, index);
        editor.commit();
    }

    /**
     * Guarda se o widget esta ativo ou nao
     *
     * @param
     */
    public void saveWidgetAtivo(boolean ativo) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_ATIVO_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WIDGET_ATIVO_PREFERENCE, ativo);
        editor.commit();
    }

    public boolean isWidgetAtivo(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_ATIVO_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(WIDGET_ATIVO_PREFERENCE, false);
    }

    /**
     * Retorna o index actual da resposta no widget
     *
     * @return
     */
    public int getIndexRespostaWidget() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_INDEX_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(WIDGET_INDEX_PREFERENCE, 0);
    }

    /**
     * Guarda a resposta actua do widget
     *
     * @param respostaActual
     */
    public void saveRespostaActualWidget(String respostaActual) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_RESPOSTA_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WIDGET_RESPOSTA_PREFERENCE, respostaActual);
        editor.commit();
    }

    /**
     * Retorna o index actual da resposta no widget
     *
     * @return
     */
    public String getRespostaActualWidget() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WIDGET_RESPOSTA_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WIDGET_RESPOSTA_PREFERENCE, "");
    }


}
