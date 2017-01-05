package pt.ipp.estg.cmu.security;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import pt.ipp.estg.cmu.R;

public class SecurityAsyncTask extends AsyncTask<String, Void, String> {

    private boolean isEncrypted;//false = encripta o texto recebido por parametro, true desencripta o texto guardado
    private Context mContext;
    private ProgressDialog mProgrees;
    private SecurityManager securityManager;



    public SecurityAsyncTask(Context context, String alias, boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
        this.mContext = context;
        securityManager = new SecurityManager(mContext, alias);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgrees = new ProgressDialog(mContext);
        mProgrees.setMessage(mContext.getResources().getString(R.string.loading));
        mProgrees.show();
    }

    @Override
    protected String doInBackground(String... params) {
        if (params == null) {
            return null;
        }
        String text = params[0];
        if (isEncrypted) {
            //desencriptar
            return securityManager.decryptString();
        } else {
            //encriptar
            String result = securityManager.encryptString(text);
            return result;
        }

    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mProgrees.dismiss();
        //System.out.println("SecurityAsyncTask -----> " + result);
    }

}
