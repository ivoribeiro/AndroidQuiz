package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterOnlineScore;
import pt.ipp.estg.cmu.models.OnlineScore;


public class OnlineScoreFragment extends Fragment {

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
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new AdapterOnlineScore(getContext(), new ArrayList<OnlineScore>());
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
