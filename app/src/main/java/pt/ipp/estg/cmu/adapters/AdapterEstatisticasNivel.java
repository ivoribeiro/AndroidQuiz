package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import pt.ipp.estg.cmu.estatisticas.EstatisticasNivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.ui.AdminNovaPerguntaFragment;
import pt.ipp.estg.cmu.util.Util;

public class AdapterEstatisticasNivel extends RecyclerView.Adapter<AdapterEstatisticasNivel.ViewHolder> {

    private Context mContext;
    private List<EstatisticasNivel> mDataSet = new ArrayList<>();

    public AdapterEstatisticasNivel(Context context, ArrayList<EstatisticasNivel> estatisticasNiveis) {
        this.mContext = context;
        this.mDataSet = estatisticasNiveis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_estatisticas_nivel, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //TODO deviamos ter posto o id da nivel no nivel xD
    private void setImageResource(AdapterEstatisticasNivel.ViewHolder holder, int pos) {
        switch (mDataSet.get(pos).getNivel().getCategoria()) {
            case "Cinema":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_1));
                break;
            case "Arte":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_1));
                break;
            case "Animais":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_2));
                break;
            case "Monumentos":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_3));
                break;
            case "Viagens":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_4));
                break;
            case "Fotografia":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_5));
                break;
            case "Tecnologia":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_6));
                break;
            case "Desporto":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_7));
                break;
            case "Moda":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_8));
                break;
            case "Comida":
                holder.mImage.setBackground(mContext.getDrawable(R.drawable.img_cat_9));
                break;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        setImageResource(holder, position);
        holder.mnome_nivel.setText(mDataSet.get(position).getNivel().getNumero());
        holder.mnome_categoria.setText(mDataSet.get(position).getNivel().getCategoria());
        holder.mpontuacao_nivel.setText("" + mDataSet.get(position).getPontuacao());
        holder.mperguntas_nivel.setText("" + mDataSet.get(position).getnPerguntas());
        holder.mrespostas_certas_nivel.setText("" + mDataSet.get(position).getnRespostasCertas());
        holder.mrespostas_erradas_nivel.setText("" + mDataSet.get(position).getnRespostasErradas());
        holder.majudas_usadas_nivel.setText("" + mDataSet.get(position).getnAjudasUsadas());
        holder.mpontuacao_ganha_nivel.setText("" + mDataSet.get(position).getPontuacaoGanha());
        holder.mpontuacao_perdida_nivel.setText("" + mDataSet.get(position).getPontuacaoPerdida());
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImage;
        TextView mpontuacao_nivel;
        TextView mperguntas_nivel;
        TextView mrespostas_certas_nivel;
        TextView mrespostas_erradas_nivel;
        TextView majudas_usadas_nivel;
        TextView mpontuacao_ganha_nivel;
        TextView mpontuacao_perdida_nivel;
        TextView mnome_nivel;
        TextView mnome_categoria;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_estatisticas_nivel);
            mImage = (ImageView) itemView.findViewById(R.id.estatisticas_categoria_image);
            mnome_categoria = (TextView) itemView.findViewById(R.id.nome_categoria);
            mnome_nivel = (TextView) itemView.findViewById(R.id.nome_nivel);
            mpontuacao_nivel = (TextView) itemView.findViewById(R.id.pontuacao_nivel);
            mperguntas_nivel = (TextView) itemView.findViewById(R.id.n_perguntas_nivel);
            mrespostas_certas_nivel = (TextView) itemView.findViewById(R.id.n_respostas_certas_nivel);
            mrespostas_erradas_nivel = (TextView) itemView.findViewById(R.id.n_respostas_erradas_nivel);
            majudas_usadas_nivel = (TextView) itemView.findViewById(R.id.n_ajudas_usadas_nivel);
            mpontuacao_ganha_nivel = (TextView) itemView.findViewById(R.id.pontuacao_ganha_nivel);
            mpontuacao_perdida_nivel = (TextView) itemView.findViewById(R.id.pontuacao_perdida_nivel);
        }
    }
}
