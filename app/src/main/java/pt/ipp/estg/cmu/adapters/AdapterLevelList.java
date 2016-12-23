package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.ui.AdminNovaPerguntaFragment;
import pt.ipp.estg.cmu.ui.AdminNovoNivelFragment;
import pt.ipp.estg.cmu.ui.AdminPerguntaActivity;
import pt.ipp.estg.cmu.ui.GameActivity;
import pt.ipp.estg.cmu.util.Util;

/**
 * Adapter contendo um cardview com uma textview e uma imageview, responsavel por listar os niveis, mostrar o score obtido em cada nivel, e se esta bloqueado
 * Click listener para abrir {@GameActivity}
 */
public class AdapterLevelList extends RecyclerView.Adapter<AdapterLevelList.ViewHolder> {

    private List<Nivel> mDataSet = new ArrayList<>();
    private Context mContext;
    private Categoria mCategoria;
    private boolean isAdmin;

    public AdapterLevelList(Context context, List<Nivel> data, Categoria categoria, boolean isAdmin) {
        this.mContext = context;
        this.mDataSet = data;
        this.mCategoria = categoria;
        this.isAdmin = isAdmin;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_level_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String score = mContext.getResources().getString(R.string.txt_score);
        boolean state = mDataSet.get(position).isBloqueado();

        holder.mTitle.setText(mDataSet.get(position).getNumero());
        holder.mScore.setText(mDataSet.get(position).getPontuacao() + " " + score);

        if (!state) {
            holder.mImageState.setBackground(mContext.getDrawable(R.drawable.img_correct));
        } else {
            holder.mImageState.setBackground(mContext.getDrawable(R.drawable.img_lock));
        }

        holder.mProgressBar.setMax(mDataSet.get(position).getnPerguntas());
        holder.mProgressBar.setProgress(mDataSet.get(position).getnRespostasCertas());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdmin) {//MODO ADMIN
                    mContext.startActivity(new Intent(mContext, AdminPerguntaActivity.class)
                            .putExtra(Util.ARG_LEVEL, mDataSet.get(position)));


                } else {//MODO GAME
                    mContext.startActivity(new Intent(mContext, GameActivity.class)
                            .putExtra(Util.ARG_LEVEL, mDataSet.get(position))
                            .putExtra(Util.ARG_CATEGORIE, mCategoria)
                    );
                }
            }
        });

        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isAdmin) {//MODO ADMIN
                    ((AppCompatActivity) mContext)
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, AdminNovoNivelFragment.newInstance(null,mDataSet.get(position)))
                            .addToBackStack(Util.STACK_ADMIN)
                            .commit();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle;
        TextView mScore;
        ProgressBar mProgressBar;
        ImageView mImageState;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitle = (TextView) itemView.findViewById(R.id.level_title);
            mScore = (TextView) itemView.findViewById(R.id.score_text);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_level);
            mImageState = (ImageView) itemView.findViewById(R.id.image_state);
        }
    }

}
