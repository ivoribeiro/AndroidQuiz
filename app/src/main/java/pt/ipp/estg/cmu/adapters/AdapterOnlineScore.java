package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.dblib.models.OnlineScore;
import pt.ipp.estg.cmu.util.UtilUI;

/**
 * Created by Navega on 12/30/2016.
 */

public class AdapterOnlineScore extends RecyclerView.Adapter<AdapterOnlineScore.ViewHolder> {

    private Context mContext;
    private ArrayList<OnlineScore> mDataSet;


    public AdapterOnlineScore(Context context, ArrayList<OnlineScore> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_online_score, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mUserName.setText(mDataSet.get(position).getUsername());
        holder.mUserScore.setText(mDataSet.get(position).getScore() + " " + mContext.getResources().getString(R.string.pontos_ganhos));
        UtilUI.setAvatar(mContext, holder.mUserAvatar, mDataSet.get(position).getAvatar());

        UtilUI.setViewAnimation(mContext, holder.mLayout, R.anim.list_bottom_top, 500);
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mLayout;
        ImageView mUserAvatar;
        TextView mUserName;
        TextView mUserScore;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.online_score_item_layout);
            mUserAvatar = (ImageView) itemView.findViewById(R.id.question_image);
            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mUserScore = (TextView) itemView.findViewById(R.id.user_score);
        }
    }
}
