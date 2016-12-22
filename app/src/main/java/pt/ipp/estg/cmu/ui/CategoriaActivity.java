package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.callbacks.FingerprintControllerCallback;
import pt.ipp.estg.cmu.security.FingerprintController;
import pt.ipp.estg.cmu.security.SecurityAsyncTask;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends ActivityBase implements FingerprintControllerCallback {

    //layout
    private boolean isAdmin;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        if (isAdmin) {
            mNavigationView.setCheckedItem(R.id.nav_admin);
            buildDialog();
        } else {
            mNavigationView.setCheckedItem(R.id.nav_game);
            startFragmetCategoria();
        }
    }

    private void buildDialog() {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_sign_in);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        final EditText editText = (EditText) mDialog.findViewById(R.id.edit_pin);
        LinearLayout mLayoutFinger = (LinearLayout) mDialog.findViewById(R.id.fingerprint_layout_status);
        final ImageView imageView = (ImageView) mDialog.findViewById(R.id.fingerprint_icon_status);
        final TextView textView = (TextView) mDialog.findViewById(R.id.fingerprint_text_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //TODO handle permission dialog authorization
            mLayoutFinger.setVisibility(View.VISIBLE);
            new FingerprintController(this, imageView, textView, this);
        } else {
            mLayoutFinger.setVisibility(View.GONE);
        }

        Button ok = (Button) mDialog.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SecurityAsyncTask(getApplicationContext(), Util.APP_TAG, true) {
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        if (editText.getText().toString().equals(result)) {
                            imageView.setImageResource(R.drawable.vt_fingerprint_success);
                            textView.setText(getApplicationContext().getString(R.string.setup_fingerprint_sucesso));

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                    startFragmetCategoria();
                                }
                            }, 1000);

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


    private void startFragmetCategoria() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }

    @Override
    public void fingerprintAuthResult(boolean result) {
        if (result) {
            mDialog.dismiss();
            startFragmetCategoria();
        }
    }
}
