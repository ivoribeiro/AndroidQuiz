package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.ui.LevelActivity;
import pt.ipp.estg.cmu.util.Util;

/**
 * Adapter contendo um cardview com uma textview e uma imageview, responsavel por listar as categorias recebidas
 * Click listener para abrir {@LevelActivity}
 */
public class AdapterCategoriaGrid extends RecyclerView.Adapter<AdapterCategoriaGrid.ViewHolder> {

    private List<Categoria> mDataSet = new ArrayList<>();
    private Context mContext;
    private boolean isAdmin;
    private RecyclerView mRecycler;

    public AdapterCategoriaGrid(Context context, RecyclerView recyclerView, List<Categoria> data, boolean isAdmin) {
        this.mContext = context;
        this.mDataSet = data;
        this.mRecycler = recyclerView;
        this.isAdmin = isAdmin;
    }

    @Override
    public AdapterCategoriaGrid.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categoria_grid, parent, false);
        AdapterCategoriaGrid.ViewHolder viewHolder = new AdapterCategoriaGrid.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterCategoriaGrid.ViewHolder holder, final int position) {
        holder.mTitle.setText(mDataSet.get(position).getNome());

        if (isAdmin) {//MODO ADMIN
            if (!mDataSet.get(position).isAtiva()) {
                holder.mCardView.setAlpha(0.5f);
            }

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDataSet.get(position).isAtiva()) {
                        mContext.startActivity(new Intent(mContext, LevelActivity.class)
                                .putExtra(Util.ARG_CATEGORIE, mDataSet.get(position))
                                .putExtra(Util.ARG_ADMIN, isAdmin));
                    } else {
                        Snackbar.make(mRecycler, mContext.getString(R.string.snack_bar_categorie_info), Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mDataSet.get(position).isAtiva()) {//DESATIVAR CATEGORIA
                        mDataSet.get(position).setAtiva(false);
                        holder.mCardView.setAlpha(0.5f);
                        Snackbar.make(mRecycler, mContext.getString(R.string.snack_bar_categorie_false), Snackbar.LENGTH_LONG).show();

                    } else {//ATIVAR CATEGORIA
                        mDataSet.get(position).setAtiva(true);
                        holder.mCardView.setAlpha(1f);
                        Snackbar.make(mRecycler, mContext.getString(R.string.snack_bar_categorie_true), Snackbar.LENGTH_LONG).show();
                    }
                    return true;
                }
            });

        } else {//MODO JOGO
            if (!mDataSet.get(position).isAtiva()) {
                holder.mCardView.setVisibility(View.GONE);
            } else {
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mDataSet.get(position).isAtiva()) {
                            mContext.startActivity(new Intent(mContext, LevelActivity.class)
                                    .putExtra(Util.ARG_CATEGORIE, mDataSet.get(position))
                                    .putExtra(Util.ARG_ADMIN, isAdmin));
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle;
        ImageView mImageState;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitle = (TextView) itemView.findViewById(R.id.categoria_text);
            mImageState = (ImageView) itemView.findViewById(R.id.categoria_image);
        }
    }

}