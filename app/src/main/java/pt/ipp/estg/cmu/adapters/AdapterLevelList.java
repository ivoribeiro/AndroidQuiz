package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.content.Intent;
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
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.ui.ActivityQuestion;
import pt.ipp.estg.cmu.util.Util;

/**
 * Created by navega on 11/19/16.
 */

public class AdapterLevelList extends RecyclerView.Adapter<AdapterLevelList.ViewHolder> {

    private List<Nivel> mDataSet = new ArrayList<>();
    private Context mContext;


    public AdapterLevelList(Context context, List<Nivel> data) {
        this.mContext = context;
        this.mDataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_level_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String level = mContext.getResources().getString(R.string.txt_level);
        String score = mContext.getResources().getString(R.string.txt_score);
        boolean state = mDataSet.get(position).getPontuacaoBase() == 1 ? false : true;//TODO mudar o getPontuacaoBase para o getApropriado

        holder.mTitle.setText(level + " " + mDataSet.get(position).getNumber());
        holder.mScore.setText(mDataSet.get(position).getNumber() + " " + score);

        if (state) {
            holder.mImageState.setBackground(mContext.getDrawable(R.drawable.ic_check));
        } else {
            holder.mImageState.setBackground(mContext.getDrawable(R.drawable.ic_lock));
        }

        holder.mProgressBar.setMax(10);
        holder.mProgressBar.setProgress(5);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ActivityQuestion.class).putExtra(Util.ARG_LEVEL, mDataSet.get(position).getNumber()));
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
