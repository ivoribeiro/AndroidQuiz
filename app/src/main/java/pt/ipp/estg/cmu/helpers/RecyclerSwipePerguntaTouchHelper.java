package pt.ipp.estg.cmu.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterPerguntasList;
import pt.ipp.estg.cmu.util.FileOperations;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;


public class RecyclerSwipePerguntaTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ArrayList<Pergunta> mPerguntas;
    private RecyclerView mRecycler;
    private Context mContext;
    private PerguntaRepo mRepository;

    private AdapterPerguntasList mAdapter;

    public RecyclerSwipePerguntaTouchHelper(int dragDirs, int swipeDirs, Context context, RecyclerView recyclerView, ArrayList<Pergunta> perguntas, AdapterPerguntasList adapter) {
        super(dragDirs, swipeDirs);
        this.mContext = context;
        this.mRecycler = recyclerView;
        this.mPerguntas = perguntas;
        this.mAdapter = adapter;
        this.mRepository = new PerguntaRepo(context);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        try {
            final int position = viewHolder.getAdapterPosition();
            final Pergunta pergunta = mPerguntas.get(position);
            if (mRepository.canDelete(pergunta)) {
                Snackbar snackbar = Snackbar.make(mRecycler,
                        mContext.getResources().getString(R.string.snack_deleted) + " " + mPerguntas.get(viewHolder.getAdapterPosition()).getRespostaCerta(),
                        Snackbar.LENGTH_LONG).setAction(mContext.getResources().getString(R.string.snack_undo),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mPerguntas.add(pergunta);
                                mAdapter.notifyItemRangeRemoved(0, mPerguntas.size() - 1);
                                mAdapter.notifyItemRangeInserted(0, mPerguntas.size());
                            }
                        });

                snackbar.show();
                mRepository.deleteById(pergunta.getId());
                FileOperations.delete(pergunta.getImagem());
                mPerguntas.remove(position);
                mRecycler.removeViewAt(position);

                mAdapter.notifyDataSetChanged();
                //mAdapter.notifyItemRemoved(position);
                //mAdapter.notifyItemRangeChanged(position, mPerguntas.size());
            } else {
                mPerguntas.remove(position);
                mRecycler.removeViewAt(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mPerguntas.size());
                mPerguntas.add(pergunta);
                mAdapter.notifyItemRangeRemoved(0, mPerguntas.size() - 1);
                mAdapter.notifyItemRangeInserted(0, mPerguntas.size());
                Snackbar.make(mRecycler, mContext.getResources().getString(R.string.cant_delete), Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

}
