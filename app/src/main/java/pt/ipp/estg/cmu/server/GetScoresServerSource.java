package pt.ipp.estg.cmu.server;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterOnlineScore;
import pt.ipp.estg.dblib.models.OnlineScore;

/**
 * @author 8130031
 * @author 8130258
 *
 * Created by Navega on 12/31/2016.
 */
public class GetScoresServerSource extends AsyncTask<JSONObject, Void, ArrayList<OnlineScore>> {

    private Context mContext;
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipe;

    public GetScoresServerSource(Context context, SwipeRefreshLayout swipe, RecyclerView recyclerView) {
        mContext = context;
        mSwipe = swipe;
        mRecycler = recyclerView;
    }

    @Override
    protected ArrayList<OnlineScore> doInBackground(JSONObject... jsonObjects) {
        ArrayList<OnlineScore> scoreArrayList = new ArrayList<>();

        JSONObject jsonObject = jsonObjects[0];
        if (null != jsonObject) {
            try {
                JSONArray scores = jsonObject.getJSONArray("scores");
                for (int i = 0; i < scores.length(); ++i) {
                    JSONObject score = scores.getJSONObject(i);
                    String avatar = score.getString("avatar");
                    String username = score.getString("username");
                    String pontuacao = score.getString("pontuacao");

                    int avt = Integer.parseInt(avatar);
                    OnlineScore onlineScore = new OnlineScore(username, pontuacao, avt);
                    System.out.println(onlineScore.toString());
                    scoreArrayList.add(onlineScore);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return scoreArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<OnlineScore> onlineScores) {
        super.onPostExecute(onlineScores);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext, mContext.getResources().getInteger(R.integer.nivel_number_grid)));
        mRecycler.setAdapter(new AdapterOnlineScore(mContext, onlineScores));
        mSwipe.setRefreshing(false);
    }
}
