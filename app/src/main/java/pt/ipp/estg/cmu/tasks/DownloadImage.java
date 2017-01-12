package pt.ipp.estg.cmu.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminNovaPerguntaPreviewImageListener;
import pt.ipp.estg.cmu.util.Util;

public class DownloadImage extends AsyncTask<String, String, String> {

    private Context mContext;
    private URL mImageUrl;
    private String mFileName;

    //layout
    private Bitmap mBitMapImg = null;
    private ProgressDialog mDialog;
    private AdminNovaPerguntaPreviewImageListener mListener;

    public DownloadImage(Context context, AdminNovaPerguntaPreviewImageListener listener, String name) {
        this.mContext = context;
        this.mFileName = name;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(mContext.getResources().getString(R.string.admin_toast_download_wait));
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... args) {
        InputStream is = null;
        try {
            mImageUrl = new URL(args[0]);
            HttpURLConnection conn = (HttpURLConnection) mImageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.RGB_565;
            mBitMapImg = BitmapFactory.decodeStream(is, null, options);

            File dir = new File(Util.getAppFolderPath());
            dir.mkdirs();
            File file = new File(dir, mFileName);
            FileOutputStream fos = new FileOutputStream(file);
            mBitMapImg.compress(CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();

            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String args) {
        if (null == mBitMapImg || null == args) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.admin_toast_download_erro), Toast.LENGTH_SHORT).show();
            mDialog.dismiss();

        } else {
            if (null != mDialog) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
            mListener.setPreviewImageFromDownload(mBitMapImg);
            Toast.makeText(mContext, mContext.getResources().getString(R.string.admin_toast_download_sucesso), Toast.LENGTH_SHORT).show();
        }
    }
}