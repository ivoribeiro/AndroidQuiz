package pt.ipp.estg.cmu.util;

import android.os.Environment;

public class Util {

    //argument strings
    public static final String APP_TAG = "CMUQUIZ";
    public static final String ARG_LEVEL = "LEVEL";
    public static final String ARG_QUESTION = "QUESTION";
    public static final String ARG_CATEGORIE = "CATEGORIE";
    public static final String ARG_ADMIN = "ADMIN";

    //stack admin settings
    public static final String STACK_ADMIN = "STACK_ADMIN";

    public static String getAppFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsoluteFile().toString() + "/CMU/";
    }

}

