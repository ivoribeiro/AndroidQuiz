package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.content.Intent;
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
 * Created by Navega on 11/29/2016.
 */

public class AdapterCategoriaGrid extends RecyclerView.Adapter<AdapterCategoriaGrid.ViewHolder> {

    private List<Categoria> mDataSet = new ArrayList<>();
    private Context mContext;

    public AdapterCategoriaGrid(Context context, List<Categoria> data) {
        this.mContext = context;
        this.mDataSet = data;
    }

    @Override
    public AdapterCategoriaGrid.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categoria_grid, parent, false);
        AdapterCategoriaGrid.ViewHolder viewHolder = new AdapterCategoriaGrid.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterCategoriaGrid.ViewHolder holder, final int position) {

        holder.mTitle.setText(mDataSet.get(position).getNome());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LevelActivity.class)
                        .putExtra(Util.ARG_CATEGORIE, mDataSet.get(position))
                );
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
        ImageView mImageState;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitle = (TextView) itemView.findViewById(R.id.categoria_text);
            mImageState = (ImageView) itemView.findViewById(R.id.categoria_image);
        }
    }

}