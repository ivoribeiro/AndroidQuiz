package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.estatisticas.EstatisticasCategoria;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.ui.AdminNovaPerguntaFragment;
import pt.ipp.estg.cmu.util.Util;

public class AdapterEstatisticasCategoria extends RecyclerView.Adapter<AdapterEstatisticasCategoria.ViewHolder> {

    private Context mContext;
    private List<EstatisticasCategoria> mDataSet = new ArrayList<>();

    public AdapterEstatisticasCategoria(Context context, ArrayList<EstatisticasCategoria> estatisticasCategorias) {
        this.mContext = context;
        this.mDataSet = estatisticasCategorias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_estatisticas_categoria, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mpontuacao_categoria.setText(""+mDataSet.get(position).getPontuacao());
        holder.mperguntas_categorias.setText(""+mDataSet.get(position).getPontuacao());
        holder.mrespostas_certas_categoria.setText(""+mDataSet.get(position).getPontuacao());
        holder.mrespostas_erradas_categoria.setText(""+mDataSet.get(position).getPontuacao());
        holder.majudas_usadas_categoria.setText(""+mDataSet.get(position).getPontuacao());
        holder.mpontuacao_ganha_categoria.setText(""+mDataSet.get(position).getPontuacao());
        holder.mpontuacao_perdida_categoria.setText(""+mDataSet.get(position).getPontuacao());
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImage;
        TextView mpontuacao_categoria;
        TextView mperguntas_categorias;
        TextView mrespostas_certas_categoria;
        TextView mrespostas_erradas_categoria;
        TextView majudas_usadas_categoria;
        TextView mpontuacao_ganha_categoria;
        TextView mpontuacao_perdida_categoria;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_estatisticas_categoria);
           //mImage = (ImageView) itemView.findViewById(R.id.question_image);
            mpontuacao_categoria = (TextView) itemView.findViewById(R.id.pontuacao_categoria);
            mperguntas_categorias = (TextView) itemView.findViewById(R.id.n_perguntas_categoria);
            mrespostas_certas_categoria = (TextView) itemView.findViewById(R.id.n_respostas_certas_categoria);
            mrespostas_erradas_categoria = (TextView) itemView.findViewById(R.id.n_respostas_erradas_categoria);
            majudas_usadas_categoria = (TextView) itemView.findViewById(R.id.n_ajudas_usadas_categoria);
            mpontuacao_ganha_categoria = (TextView) itemView.findViewById(R.id.pontuacao_ganha_categoria);
            mpontuacao_perdida_categoria = (TextView) itemView.findViewById(R.id.pontuacao_perdida_categoria);
        }
    }
}
