package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterOnlineScore;
import pt.ipp.estg.cmu.enums.RequestTypeEnum;
import pt.ipp.estg.cmu.server.GetScoresServerSource;
import pt.ipp.estg.cmu.server.Request;
import pt.ipp.estg.cmu.util.Util;


public class OnlineScoreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecycler;
    private AdapterOnlineScore mAdapter;
    private SwipeRefreshLayout mSwipe;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_score, container, false);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
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


}
