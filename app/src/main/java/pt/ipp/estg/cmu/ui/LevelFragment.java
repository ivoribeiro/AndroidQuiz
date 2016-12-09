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
import pt.ipp.estg.cmu.util.Util;

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
    private boolean isAdmin;

    //data
    private AdapterLevelList mAdapter;
    private Categoria mCategoria;
    private ArrayList<Nivel> mNiveis;
    private NivelRepo mRepository;

    public LevelFragment() {
    }

    public static LevelFragment newInstance(Categoria categoria, boolean isAdmin) {
        Bundle args = new Bundle();
        LevelFragment fragment = new LevelFragment();
        args.putParcelable(KEY_CATEGORIA, categoria);
        args.putBoolean(Util.ARG_ADMIN, isAdmin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoria = getArguments().getParcelable(KEY_CATEGORIA);
            isAdmin = getArguments().getBoolean(Util.ARG_ADMIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mRepository = new NivelRepo(getContext());
        mNiveis = getAllNiveis();
        mAdapter = new AdapterLevelList(getActivity(), mNiveis, mCategoria,isAdmin);
        mRecyclerView.setAdapter(mAdapter);

        if (isAdmin) {
            //RecyclerTouchHelper swipeTouch = new RecyclerTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, mNiveis, mCategoria);
            //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeTouch);
            //itemTouchHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    private ArrayList<Nivel> getAllNiveis() {
        return this.mRepository.getAllByCategoria(this.mCategoria.getNome());
    }
}
