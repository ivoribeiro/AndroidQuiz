package pt.ipp.estg.cmu.callbacks;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.security.SecurityAsyncTask;
import pt.ipp.estg.cmu.setup.PreferencesSetup;
import pt.ipp.estg.cmu.util.Util;

/**
 * Created by Navega on 12/21/2016.
 */

public class TextWatcherHelper implements TextWatcher {

    private Context mContext;
    private EditText mEditText;

    private PreferencesSetup mPreferencesSetup;

    public TextWatcherHelper(Context mContext, EditText mEditText) {
        this.mContext = mContext;
        this.mEditText = mEditText;
        mPreferencesSetup = new PreferencesSetup(mContext);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //nada a fazer
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //nada a fazer
    }

    @Override
    public void afterTextChanged(Editable editable) {
        switch (mEditText.getId()) {
            case R.id.edit_nickname://edittext do nickname no setup
                mPreferencesSetup.saveFlagNickNamePreference(editable.toString());
                break;

            case R.id.edit_pin://edittext do nickname no setup
                new SecurityAsyncTask(mContext, Util.APP_TAG, false).execute(editable.toString());
                break;
        }
    }
}
