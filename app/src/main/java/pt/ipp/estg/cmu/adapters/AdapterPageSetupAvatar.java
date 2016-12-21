package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.callbacks.AdapterPageSetupCallback;

/**
 * Created by Navega on 12/21/2016.
 */

public class AdapterPageSetupAvatar extends RecyclerView.Adapter<AdapterPageSetupAvatar.ViewHolder> {

    private Context mContext;
    private AdapterPageSetupCallback mListener;

    public AdapterPageSetupAvatar(Context context, AdapterPageSetupCallback listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public AdapterPageSetupAvatar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_page_setup_avatar, parent, false);
        AdapterPageSetupAvatar.ViewHolder viewHolder = new AdapterPageSetupAvatar.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        switch (position) {
            case 0:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_0));
                break;
            case 1:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_1));
                break;
            case 2:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_2));
                break;
            case 3:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_3));
                break;
            case 4:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_4));
                break;
            case 5:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_5));
                break;
            case 6:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_6));
                break;
            case 7:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_7));
                break;
            case 8:
                holder.mImageView.setBackground(mContext.getDrawable(R.drawable.img_avatar_8));
                break;
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRecyclerItemSelected(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return 9;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

}