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
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;

/**
 * A placeholder fragment containing a simple view.
 */
public class LevelFragment extends Fragment {

    //KEY
    private static final String KEY_LEVEL = "KEY_LEVEL";
    private static final String KEY_CATEGORIA = "KEY_CATEGORIA";

    //layout
    private int NUM_COLUMNS = 1;
    private RecyclerView mRecyclerView;

    //data
    private AdapterLevelList mAdapter;
    private Categoria mCategoria;
    private NivelRepo repo;

    public LevelFragment() {
    }

    public static LevelFragment newInstance(Categoria categoria) {
        Bundle args = new Bundle();
        LevelFragment fragment = new LevelFragment();
        args.putParcelable(KEY_CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_level, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        mRecyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.repo = new NivelRepo(getContext());
        mCategoria = getArguments().getParcelable(KEY_CATEGORIA);
        mAdapter = new AdapterLevelList(getActivity(), getAllNiveis(), mCategoria);
        mRecyclerView.setAdapter(mAdapter);
    }


    private ArrayList<Nivel> getAllNiveis() {
        return this.repo.getAllByCategoria(this.mCategoria.getNome());
    }
}
