package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import pt.ipp.estg.cmu.setup.PreferencesSetup;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends AppCompatActivity implements FingerprintControllerCallback {

    //layout
    private boolean isAdmin;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        if (isAdmin) {
            buildDialog();
        } else {
            startFragmetCategoria();
        }
    }


    private void buildDialog() {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_sign_in);
        mDialog.setTitle(getResources().getString(R.string.dialog_title));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        final EditText editText = (EditText) mDialog.findViewById(R.id.edit_pin);
        LinearLayout mLayoutFinger = (LinearLayout) mDialog.findViewById(R.id.fingerprint_layout_status);
        ImageView imageView = (ImageView) mDialog.findViewById(R.id.fingerprint_icon_status);
        final TextView textView = (TextView) mDialog.findViewById(R.id.fingerprint_text_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                            mDialog.dismiss();
                            startFragmetCategoria();
                        }else {

                            textView.setText("Error");

                        }
                    }
                }.execute("");
            }
        });

        mDialog.show();
        mDialog.getWindow().setAttributes(lp);
    }


    public void startFragmetCategoria() {
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

    @Override
    public void onBackPressed() {

    }
}
