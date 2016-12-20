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
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.ui.AdminNovaPerguntaFragment;
import pt.ipp.estg.cmu.util.Util;

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
        String imagePath = mDataSet.get(position).getImagem();
        if (null != imagePath) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.mImage.setImageBitmap(bmImg);
            }
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) mContext)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, AdminNovaPerguntaFragment.newInstance(null, mDataSet.get(position)))
                        .addToBackStack(Util.STACK_ADMIN)
                        .commit();
            }
        });
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