package pt.ipp.estg.cmu.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by Navega on 12/27/2016.
 */

public class EstatisticasGraphs extends Fragment {

    private PieChart mChart;

    public EstatisticasGraphs() {
    }

    public static EstatisticasGraphs newInstance() {
        EstatisticasGraphs fragment = new EstatisticasGraphs();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas_graphs, container, false);

        mChart = (PieChart) view.findViewById(R.id.jogo_pie);

        mChart.setUsePercentValues(true);

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(25);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);


        setDataForPieChart();

        Legend l = mChart.getLegend();
        l.setFormSize(15f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        l.setTextSize(18f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);

        mChart.animateXY(3000, 3000);

        return view;
    }


    private void setDataForPieChart() {
        List<PieEntry> entries = new ArrayList<>();


        entries.add(new PieEntry(18.5f, "Certas"));
        entries.add(new PieEntry(26.7f, "Erradas"));
        //entries.add(new PieEntry(24.0f, "Red"));
        //entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(set);
        mChart.setData(data);
        mChart.invalidate(); // refresh
    }

}
