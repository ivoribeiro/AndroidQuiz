package pt.ipp.estg.cmu.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, String, String> {
    private Context context;
    private ProgressDialog pDialog;
    private URL ImageUrl;
    private Bitmap bmImg = null;

    private String mCategoriaName;
    private String mNivelName;
    private String mIndexName;

    public DownloadImage(Context context, String categoria, String nivel, String index) {
        this.context = context;
        this.mCategoriaName = categoria;
        this.mNivelName = nivel;
        mIndexName = index;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub

        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected String doInBackground(String... args) {
        InputStream is = null;
        try {

            ImageUrl = new URL(args[0]);
            HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.RGB_565;
            bmImg = BitmapFactory.decodeStream(is, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/CMU/");
            dir.mkdirs();
            String fileName = mCategoriaName + "_" + mNivelName + "_" + mIndexName + ".jpg";
            File file = new File(dir, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bmImg.compress(CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();

/*            File imageFile = file;
            MediaScannerConnection.scanFile(context,
                    new String[]{imageFile.getPath()},
                    new String[]{"image/jpeg"}, null);*/

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
        // TODO Auto-generated method stub

        if (bmImg == null) {

            Toast.makeText(context, "Image still loading...",
                    Toast.LENGTH_SHORT).show();

            pDialog.dismiss();

        } else {

            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }

            Toast.makeText(context, "Wallpaper Successfully Saved",
                    Toast.LENGTH_SHORT).show();

        }
    }

}