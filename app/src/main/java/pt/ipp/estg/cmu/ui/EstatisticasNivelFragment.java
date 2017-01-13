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
import pt.ipp.estg.cmu.adapters.AdapterEstatisticasNivel;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.estatisticas.EstatisticasNivel;
import pt.ipp.estg.dblib.models.Nivel;

/**
 * @author 8130031
 * @author 8130258
 */
public class EstatisticasNivelFragment extends Fragment {

    private ArrayList<EstatisticasNivel> mEstatisticasNivel;
    private RecyclerView mRecycler;
    private AdapterEstatisticasNivel mAdapter;
    private int NUM_GRID;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EstatisticasNivelFragment newInstance() {
        EstatisticasNivelFragment fragment = new EstatisticasNivelFragment();
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
        View view = inflater.inflate(R.layout.fragment_estatisticas_nivel, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), NUM_GRID));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new AdapterEstatisticasNivel(getContext(), mEstatisticasNivel);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEstatisticasNivel = new ArrayList<>();
        ArrayList<Nivel> niveis = new NivelRepo(context).getAll();
        for (Nivel nivel : niveis) {
            mEstatisticasNivel.add(new EstatisticasNivel(context, nivel));
        }
    }

}
