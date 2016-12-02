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
import pt.ipp.estg.cmu.adapters.AdapterCategoriaGrid;
import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.models.Categoria;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriaFragment extends Fragment {

    private static final int NUM_GRID = 2;
    private CategoriaRepo repo;

    private ArrayList<Categoria> mCategorias;

    //layout
    private RecyclerView mRecycler;
    private AdapterCategoriaGrid mAdapter;

    public static CategoriaFragment newInstance() {
        CategoriaFragment fragment = new CategoriaFragment();
        return fragment;
    }

    public CategoriaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_categoria, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), NUM_GRID));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.repo = new CategoriaRepo(getContext());
        mCategorias = getAllCategorias();
        mAdapter = new AdapterCategoriaGrid(getContext(), mCategorias);
        mRecycler.setAdapter(mAdapter);
    }


    private ArrayList<Categoria> getAllCategorias() {
        return this.repo.getAll();
    }
}
