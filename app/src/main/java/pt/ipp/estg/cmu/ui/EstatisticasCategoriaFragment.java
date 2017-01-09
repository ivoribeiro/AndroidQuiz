package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterEstatisticasCategoria;
import pt.ipp.estg.dblib.repositories.CategoriaRepo;
import pt.ipp.estg.dblib.estatisticas.EstatisticasCategoria;
import pt.ipp.estg.dblib.models.Categoria;

public class EstatisticasCategoriaFragment extends Fragment {

    private ArrayList<EstatisticasCategoria> mEstatisticasCategorias;
    private RecyclerView mRecycler;
    private AdapterEstatisticasCategoria mAdapter;
    private int NUM_GRID;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EstatisticasCategoriaFragment newInstance() {
        EstatisticasCategoriaFragment fragment = new EstatisticasCategoriaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NUM_GRID = getContext().getResources().getInteger(R.integer.nivel_number_grid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas_categoria, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), NUM_GRID));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEstatisticasCategorias = new ArrayList<>();
        ArrayList<Categoria> categorias = new CategoriaRepo(context).getAll();
        for (Categoria categoria : categorias) {
            mEstatisticasCategorias.add(new EstatisticasCategoria(context, categoria.getNome()));
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new AdapterEstatisticasCategoria(getContext(), mEstatisticasCategorias);
        mRecycler.setAdapter(mAdapter);

    }
}
