package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterLevelList;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityLevelFragment extends Fragment {

    //KEY
    private static final String KEY_LEVEL = "KEY_LEVEL";
    private static final String KEY_CATEGORIA = "KEY_CATEGORIA";

    //layout
    private int NUM_COLUMNS = 1;
    private RecyclerView mRecyclerView;

    //data
    private AdapterLevelList mAdapter;
    private ArrayList<Nivel> mNiveis;
    private Categoria mCategoria;

    public ActivityLevelFragment() {
    }

    public static ActivityLevelFragment newInstance(ArrayList<Nivel> list, Categoria categoria) {
        Bundle args = new Bundle();
        ActivityLevelFragment fragment = new ActivityLevelFragment();
        args.putParcelableArrayList(KEY_LEVEL, list);
        args.putParcelable(KEY_CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNiveis = getArguments().getParcelableArrayList(KEY_LEVEL);
        mCategoria = getArguments().getParcelable(KEY_CATEGORIA);
        mAdapter = new AdapterLevelList(getActivity(), mNiveis, mCategoria);
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
