package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterOnlineScore;
import pt.ipp.estg.cmu.enums.RequestTypeEnum;
import pt.ipp.estg.cmu.helpers.RecyclerOnScrollListenerHelper;
import pt.ipp.estg.cmu.server.GetScoresServerSource;
import pt.ipp.estg.cmu.server.JsonBuilder;
import pt.ipp.estg.cmu.server.Request;
import pt.ipp.estg.cmu.setup.PreferencesSetup;
import pt.ipp.estg.cmu.util.Util;
import pt.ipp.estg.dblib.estatisticas.EstatisticasJogo;


public class OnlineScoreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipe;
    private FloatingActionButton mFab;

    private EstatisticasJogo mEstatisticasSource;
    private PreferencesSetup mPreferencesSetup;
    private String mUserName;
    private int mPontos;

    public OnlineScoreFragment() {
        // Required empty public constructor
    }

    public static OnlineScoreFragment newInstance() {
        OnlineScoreFragment fragment = new OnlineScoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferencesSetup = new PreferencesSetup(getContext());
        mUserName = mPreferencesSetup.getFlagNickNamePreference();

        mEstatisticasSource = new EstatisticasJogo(getContext());
        mPontos = mEstatisticasSource.getPontuacao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_score, container, false);

        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.addOnScrollListener(new RecyclerOnScrollListenerHelper(mFab));

        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipe.setOnRefreshListener(this);

        callGetScores();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        callGetScores();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            callUpdateScore();
        }
    }

    private void callGetScores() {
        new Request(RequestTypeEnum.GET, getContext(), null) {
            @Override
            public void onPostExecute(JSONObject data) {
                super.onPostExecute(data);
                mSwipe.setRefreshing(false);
                new GetScoresServerSource(getContext(), mSwipe, mRecycler).execute(data);
            }
        }.execute(Util.SERVER_GET_SCORES);
    }

    private void callUpdateScore() {
        String json = JsonBuilder.BUILD_UPDATE_SCORE(mUserName, mPontos);
        new Request(RequestTypeEnum.POST, getContext(), json) {
            @Override
            public void onPostExecute(JSONObject data) {
                super.onPostExecute(data);
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.send_sucess), Toast.LENGTH_SHORT).show();
            }
        }.execute(Util.SERVER_UPDATE_PLAYER_SCORE);
    }
}
