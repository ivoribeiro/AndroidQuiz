package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterLevelList;
import pt.ipp.estg.cmu.models.Nivel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityLevelFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //layout
    private int NUM_COLUMNS = 1;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipe;
    //data
    private AdapterLevelList mAdapter;

    public ActivityLevelFragment() {
    }

    public static ActivityLevelFragment newInstance() {
        Bundle args = new Bundle();
        ActivityLevelFragment fragment = new ActivityLevelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Nivel> list = new ArrayList<>();
        list.add(new Nivel("1", 10, 1, 1, 1, false));
        list.add(new Nivel("2", 10, 0, 1, 1, true));
        list.add(new Nivel("3", 10, 1, 1, 1, true));
        list.add(new Nivel("4", 10, 0, 1, 1, true));
        list.add(new Nivel("5", 10, 1, 1, 1, true));
        list.add(new Nivel("6", 10, 0, 1, 1, true));
        list.add(new Nivel("7", 10, 0, 1, 1, true));
        list.add(new Nivel("8", 10, 0, 1, 1, true));

        mAdapter = new AdapterLevelList(getActivity(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_level, container, false);

        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipe.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onRefresh() {
        mSwipe.setRefreshing(false);
    }
}
