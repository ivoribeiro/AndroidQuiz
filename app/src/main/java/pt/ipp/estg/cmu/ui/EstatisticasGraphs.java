package pt.ipp.estg.cmu.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterEstatisticasGraph;


/**
 * @author 8130031
 * @author 8130258
 */
public class EstatisticasGraphs extends Fragment {

    private RecyclerView mRecycler;
    private AdapterEstatisticasGraph mAdapter;
    private int NUM_GRID;


    public EstatisticasGraphs() {
    }

    public static EstatisticasGraphs newInstance() {
        EstatisticasGraphs fragment = new EstatisticasGraphs();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NUM_GRID = getContext().getResources().getInteger(R.integer.nivel_number_grid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas_graphs, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), NUM_GRID));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new AdapterEstatisticasGraph(getContext());
        mRecycler.setAdapter(mAdapter);

    }
}
