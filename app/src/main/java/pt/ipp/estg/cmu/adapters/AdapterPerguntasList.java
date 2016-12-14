package pt.ipp.estg.cmu.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
import pt.ipp.estg.cmu.models.Pergunta;

/**
 * Created by Navega on 12/10/2016.
 */

public class AdapterPerguntasList extends RecyclerView.Adapter<AdapterPerguntasList.ViewHolder> {

    private Context mContext;
    private List<Pergunta> mDataSet = new ArrayList<>();

    public AdapterPerguntasList(Context context, ArrayList<Pergunta> perguntas) {
        this.mContext = context;
        this.mDataSet = perguntas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pergunta_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mAnswerText.setText(mDataSet.get(position).getRespostaCerta());
        File imgFile = new File(mDataSet.get(position).getImagem());
        if (imgFile.exists()) {
            Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.mImage.setImageBitmap(bmImg);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImage;
        TextView mAnswerText;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mImage = (ImageView) itemView.findViewById(R.id.question_image);
            mAnswerText = (TextView) itemView.findViewById(R.id.answer_title);
        }
    }


}
