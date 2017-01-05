package pt.ipp.estg.cmu.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.FingerprintControllerCallback;
import pt.ipp.estg.cmu.security.FingerprintController;
import pt.ipp.estg.cmu.security.SecurityAsyncTask;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends ActivityBase implements FingerprintControllerCallback {

    //layout
    private boolean isAdmin;
    private Dialog mDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_categoria);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);
        if (isAdmin) {
            super.mActualToolbar.setSubtitle(getResources().getString(R.string.admin_toolbar_subtitle));
        }

        if (isAdmin) {
            mNavigationView.setCheckedItem(R.id.nav_admin);
            if (null == savedInstanceState) {
                buildDialog();
            }
        } else {
            mNavigationView.setCheckedItem(R.id.nav_game);
            if (null == savedInstanceState) {
                startFragmetCategoria();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void buildDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.window_admin_sign_in);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        final EditText editText = (EditText) mDialog.findViewById(R.id.edit_pin);
        final ImageView imageView = (ImageView) mDialog.findViewById(R.id.fingerprint_icon_status);
        final TextView textView = (TextView) mDialog.findViewById(R.id.fingerprint_text_status);

        new FingerprintController(this, imageView, textView, this);

        final Activity activity = this;

        Button ok = (Button) mDialog.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SecurityAsyncTask(activity, Util.APP_TAG, true) {
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        if (editText.getText().toString().equals(result)) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                            imageView.setBackground(null);
                            imageView.setImageResource(R.drawable.vt_fingerprint_success);
                            textView.setText(getApplicationContext().getString(R.string.setup_fingerprint_sucesso));

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                    startFragmetCategoria();
                                }
                            }, 500);

                        } else {
                            imageView.setImageResource(R.drawable.vt_fingerprint_error);
                            textView.setText(getApplicationContext().getString(R.string.setup_fingerprint_error));
                        }
                    }
                }.execute("");
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled()) {
                    return true;
                }
                return false;
            }
        });

        mDialog.show();
        mDialog.getWindow().setAttributes(lp);
    }

    @Override
    public void fingerprintAuthResult(boolean result) {
        if (result) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            mDialog.dismiss();
            startFragmetCategoria();
        }
    }

    private void startFragmetCategoria() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }
}
