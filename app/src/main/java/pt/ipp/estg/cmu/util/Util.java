package pt.ipp.estg.cmu.util;

import android.os.Environment;

public class Util {

    //argument strings
    public static final String ARG_LEVEL = "LEVEL";
    public static final String ARG_QUESTION = "QUESTION";
    public static final String ARG_CATEGORIE = "CATEGORIE";
    public static final String ARG_ADMIN = "ADMIN";


    public static String getAppFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsoluteFile().toString() + "/CMU/";
    }

}

