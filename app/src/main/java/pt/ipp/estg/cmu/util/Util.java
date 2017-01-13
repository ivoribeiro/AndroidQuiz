package pt.ipp.estg.cmu.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * @author 8130031
 * @author 8130258
 */
public class Util {

    //server strings
    public static final String SERVER = "http://androidquiz.ddns.net/api/";
    public static final String SERVER_CREATE_PLAYER = "createPlayer";
    public static final String SERVER_GET_SCORES = "getScores";
    public static final String SERVER_UPDATE_PLAYER_SCORE = "updatePlayerScore";


    //argument strings
    public static final String APP_TAG = "CMUQUIZ";
    public static final String ARG_LEVEL = "LEVEL";
    public static final String ARG_QUESTION = "QUESTION";
    public static final String ARG_CATEGORIE = "CATEGORIE";
    public static final String ARG_ADMIN = "ADMIN";
    public static final String ARG_SECTION = "SECTION";
    public static final String ARG_ORIENTATION = "ORIENTATION";
    public static final String ARG_SCREEEN = "SCREEN";
    public static final String ARG_IMAGE = "IMAGE";
    public static final String ARG_EDIT = "EDIT";
    public static final String ARG_IMAGE_NAME = "IMAGE";

    //request code ints
    public static final int PERMISSIONS_REQUEST_WRITE_GALERIA = 0;
    public static final int PERMISSIONS_REQUEST_WRITE_DOWNLOAD = 1;


    //instancestate
    public static final int PERMISSIONS_REQUEST_CAMERA = 2;


    //stack admin settings
    public static final String STACK_ADMIN = "STACK_ADMIN";

    public static String getAppFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsoluteFile().toString() + "/CMU/";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isFingerprintReady(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                if (!fingerprintManager.isHardwareDetected()) {
                    return false;
                } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                    return false;
                } else {
                    return true;
                }
            } else {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.USE_FINGERPRINT)) {


                    System.out.println("asd");
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.USE_FINGERPRINT}, 0);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
        return false;
    }

}

