package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import pt.ipp.estg.dblib.estatisticas.EstatisticasJogo;

/**
 * @author 8130031
 * @author 8130258
 */
public class AdapterEstatisticasGraph extends RecyclerView.Adapter<AdapterEstatisticasGraph.ViewHolder> {

    private Context mContext;

    public AdapterEstatisticasGraph(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_estatisticas_graph, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        EstatisticasJogo estatisticasJogo = new EstatisticasJogo(mContext);

        switch (position) {
            case 0:
                int totalrespondidas = estatisticasJogo.getnRespostasCertas() + estatisticasJogo.getnRespostasErradas();
                float certas;
                float erradas;
                if (totalrespondidas > 0) {
                    certas = estatisticasJogo.getnRespostasCertas() * 100 / totalrespondidas;
                    erradas = estatisticasJogo.getnRespostasErradas() * 100 / totalrespondidas;
                } else {
                    certas = 0;
                    erradas = 0;
                }
                setupPie(holder.mPie, 0, new float[]{certas, erradas}, new String[]{"%Certas", "%Erradas"});
                break;


            case 1:
                int pontuacaototal = estatisticasJogo.getPontuacaoGanha() + estatisticasJogo.getPontuacaoPerdida();
                float pontPerdida;
                float pontGanha;
                if (pontuacaototal > 0) {
                    pontPerdida = estatisticasJogo.getPontuacaoPerdida() * 100 / pontuacaototal;
                    pontGanha = estatisticasJogo.getPontuacaoGanha() * 100 / pontuacaototal;
                } else {
                    pontPerdida = 0;
                    pontGanha = 0;
                }
                setupPie(holder.mPie, 1, new float[]{pontGanha, pontPerdida}, new String[]{"%Ganha", "%Perdida"});
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }


    private void setupPie(PieChart pieChart, int pos, float dados[], String labels[]) {


        List<PieEntry> entries = new ArrayList<>();
        int index = 0;
        for (float dado : dados) {
            entries.add(new PieEntry(dado, labels[index]));
            index++;
        }

        //entries.add(new PieEntry(24.0f, "Red"));
        //entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "");
        Description description = new Description();
        description.setText(" ");
        pieChart.setDescription(description);
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate();


        Legend l = pieChart.getLegend();
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

        pieChart.animateXY(3000, 3000);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        PieChart mPie;
        //TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            mPie = (PieChart) itemView.findViewById(R.id.pie_chart);
            //title = (TextView) itemView.findViewById(R.id.title_estatisticas_graph);
        }
    }
}
