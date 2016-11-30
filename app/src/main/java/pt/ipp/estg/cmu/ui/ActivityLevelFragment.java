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
public class ActivityLevelFragment extends Fragment {

    //KEY
    private static final String KEY_LEVEL = "KEY_LEVEL";

    //layout
    private int NUM_COLUMNS = 1;
    private RecyclerView mRecyclerView;

    //data
    private AdapterLevelList mAdapter;
    private ArrayList<Nivel> mNiveis;


    public ActivityLevelFragment() {
    }

    public static ActivityLevelFragment newInstance(ArrayList<Nivel> list) {
        Bundle args = new Bundle();
        ActivityLevelFragment fragment = new ActivityLevelFragment();
        args.putParcelableArrayList(KEY_LEVEL, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNiveis = getArguments().getParcelableArrayList(KEY_LEVEL);
        mAdapter = new AdapterLevelList(getActivity(), mNiveis);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_level, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


}
