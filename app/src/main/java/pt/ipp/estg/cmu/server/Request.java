package pt.ipp.estg.cmu.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import pt.ipp.estg.cmu.enums.RequestTypeEnum;
import pt.ipp.estg.cmu.util.Util;


public class Request extends AsyncTask<String, Boolean, JSONObject> {

    private static final int HTTP_TIMEOUT = 5000;

    private RequestTypeEnum mRequestType;
    private Context mContext;
    private String mStringBuild;

    private ProgressDialog mProgrees;

    public Request(RequestTypeEnum type, Context context, String stringBuild) {
        mRequestType = type;
        mContext = context;
        mStringBuild = stringBuild;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgrees = new ProgressDialog(mContext);
        mProgrees.setMessage("Loading");
        mProgrees.show();
    }

    protected JSONObject doInBackground(String... urls) {
        //if (Util.hasInternetAccess(this.mContext) == false) {
        //    this.hasInternet = false;
        //    publishProgress(false);
        //}

        String json = null;
        JSONObject jsonObject = null;
        try {
            URL url = new URL(Util.SERVER + urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json");

            if (mRequestType == RequestTypeEnum.GET) {
                //Server response
                connection.setRequestMethod("GET");
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                json = read(rd);
                jsonObject = new JSONObject(json);

            } else {
                //Server post
                connection.setRequestMethod("POST");
                if (mStringBuild != null) {
                    DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
                    writer.write(mStringBuild.getBytes("UTF-8"));
                    writer.flush();
                    writer.close();
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                    json = read(rd);
                    jsonObject = new JSONObject(json);
                }
            }


            int responseCode = connection.getResponseCode();
            //System.out.println("CODE" + responseCode);

        } catch (IOException e) {
            System.out.println(e);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isCancelled()) {
            publishProgress(true);
            return null;
        }

        return jsonObject;
    }


    public void onPostExecute(JSONObject data) {
        mProgrees.dismiss();
    }


    private String read(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        String temp = "";
        while ((temp = rd.readLine()) != null) {
            sb.append(temp);
        }
        return sb.toString();
    }
}