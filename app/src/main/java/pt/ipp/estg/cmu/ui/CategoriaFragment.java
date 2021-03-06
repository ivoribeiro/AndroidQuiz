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
import pt.ipp.estg.dblib.repositories.CategoriaRepo;
import pt.ipp.estg.dblib.models.Categoria;
import pt.ipp.estg.cmu.util.Util;

/**
 * @author 8130031
 * @author 8130258
 *
 * Fragment contendo um recyclerview, que apos obter data, instacia o {@AdapterCategoriaGrid}
 * Fragment responsavel por fazer mostrar as categorias em dois contextos diferentes, em modo de jogo e em modo admin
 */
public class CategoriaFragment extends Fragment {

    private int NUM_GRID;
    //data
    private CategoriaRepo mRepository;
    private ArrayList<Categoria> mCategorias;

    //layout
    private RecyclerView mRecyclerView;
    private AdapterCategoriaGrid mAdapter;
    private boolean isAdmin;

    public static CategoriaFragment newInstance(boolean isAdmin) {
        CategoriaFragment fragment = new CategoriaFragment();
        Bundle args = new Bundle();
        args.putBoolean(Util.ARG_ADMIN, isAdmin);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean(Util.ARG_ADMIN);
        }
        NUM_GRID = getContext().getResources().getInteger(R.integer.categoria_number_grid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_categoria, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUM_GRID));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRepository = new CategoriaRepo(getContext());
        mCategorias = mRepository.getAll();
        mAdapter = new AdapterCategoriaGrid(getContext(), mRecyclerView, mCategorias, isAdmin);
        mRecyclerView.setAdapter(mAdapter);
    }
}
