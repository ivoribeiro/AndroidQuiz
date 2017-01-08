package pt.ipp.estg.cmu.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminLoginListener;
import pt.ipp.estg.cmu.interfaces.FingerprintControllerCallback;
import pt.ipp.estg.cmu.security.FingerprintController;
import pt.ipp.estg.cmu.security.SecurityAsyncTask;
import pt.ipp.estg.cmu.util.Util;

/**
 * Created by Navega on 1/8/2017.
 */

public class CategoriaAdminDialog extends DialogFragment implements View.OnClickListener, FingerprintControllerCallback {


    private AdminLoginListener mListener;

    private EditText mEditText;
    private ImageView mImageView;
    private TextView mTextView;
    private Button ok;

    public CategoriaAdminDialog() {
    }

    public static CategoriaAdminDialog newInstance() {
        CategoriaAdminDialog dialog = new CategoriaAdminDialog();
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.window_admin_sign_in, container, false);

        mEditText = (EditText) rootView.findViewById(R.id.edit_pin);
        mImageView = (ImageView) rootView.findViewById(R.id.fingerprint_icon_status);
        mTextView = (TextView) rootView.findViewById(R.id.fingerprint_text_status);

        ok = (Button) rootView.findViewById(R.id.dialog_ok);

        ok.setOnClickListener(this);


        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FingerprintController(getActivity(), mImageView, mTextView, this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AdminLoginListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (AdminLoginListener) activity;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_ok) {
            callLogin();
        }
    }

    private void callLogin() {
        new SecurityAsyncTask(getActivity(), Util.APP_TAG, true) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (mEditText.getText().toString().equals(result)) {

                    mImageView.setBackground(null);
                    mImageView.setImageResource(R.drawable.vt_fingerprint_success);
                    mTextView.setText(getActivity().getString(R.string.setup_fingerprint_sucesso));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                            mListener.onLoginSucess();
                            //startFragmetCategoria();
                        }
                    }, 500);

                } else {
                    mImageView.setBackground(null);
                    mImageView.setImageResource(R.drawable.vt_fingerprint_error);
                    mTextView.setText(getActivity().getString(R.string.setup_fingerprint_error));
                }
            }
        }.execute("");
    }

    @Override
    public void fingerprintAuthResult(boolean result) {
        if (result) {
            dismiss();
            mListener.onLoginSucess();
        }
    }
}
