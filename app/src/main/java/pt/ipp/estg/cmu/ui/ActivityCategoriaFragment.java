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
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterCategoriaGrid;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityCategoriaFragment extends Fragment {

    private static final int NUM_GRID = 2;
    private static final String KEY_CAT = "KEY_CAT";

    private RecyclerView mRecycler;
    private AdapterCategoriaGrid mAdapter;
    private ArrayList<Categoria> mCategorias;

    public static ActivityCategoriaFragment newInstance(ArrayList<Categoria> list) {
        Bundle args = new Bundle();
        ActivityCategoriaFragment fragment = new ActivityCategoriaFragment();
        args.putParcelableArrayList(KEY_CAT, list);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityCategoriaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategorias = getArguments().getParcelableArrayList(KEY_CAT);
        mAdapter = new AdapterCategoriaGrid(getContext(), mCategorias);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_categoria, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), NUM_GRID));
        mRecycler.setAdapter(mAdapter);

        return view;
    }
}
