package pt.ipp.estg.cmu.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterLevelList;
import pt.ipp.estg.cmu.util.FileOperations;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;
import pt.ipp.estg.dblib.repositories.RepositoryInterface;

/**
 * @author 8130031
 * @author 8130258
 */
public class RecyclerSwipeNivelTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ArrayList<Nivel> mNiveis;
    private RecyclerView mRecycler;
    private Context mContext;
    private RepositoryInterface<Nivel> mRepository;
    private PerguntaRepo mRepositoryPergunta;

    private AdapterLevelList mAdapter;

    public RecyclerSwipeNivelTouchHelper(int dragDirs, int swipeDirs, Context context, RecyclerView recyclerView, ArrayList<Nivel> nivels, AdapterLevelList adapter) {
        super(dragDirs, swipeDirs);
        this.mContext = context;
        this.mRecycler = recyclerView;
        this.mNiveis = nivels;
        this.mAdapter = adapter;
        this.mRepository = new NivelRepo(context);
        mRepositoryPergunta = new PerguntaRepo(context);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        try {
            final int position = viewHolder.getAdapterPosition();
            final Nivel nivel = mNiveis.get(position);
            //so se pode apagar se o nivel ainda nao tiver sido iniciado
            if (mRepository.canDelete(nivel)) {
                Snackbar.make(mRecycler, mContext.getResources().getString(R.string.snack_deleted) + " " + mNiveis.get(viewHolder.getAdapterPosition()).getNumero(),
                        Snackbar.LENGTH_LONG).setAction(mContext.getResources().getString(R.string.snack_undo),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mNiveis.add(nivel);
                                mAdapter.notifyItemRangeRemoved(0, mNiveis.size() - 1);
                                mAdapter.notifyItemRangeInserted(0, mNiveis.size());
                                //mAdapter.notifyDataSetChanged();
                                //mRecycler.setAdapter(new AdapterCategoriaGrid(mContext, mCategorias, true));
                            }
                        }).show();
                int idNivel;
                idNivel = nivel.getId();
                ArrayList<Pergunta> perguntas = mRepositoryPergunta.getAllByNivel(idNivel);
                for (Pergunta pergunta : perguntas) {
                    FileOperations.delete(pergunta.getImagem());
                }
                mRepository.deleteById(idNivel);
                mNiveis.remove(position);
                mRecycler.removeViewAt(position);

                mAdapter.notifyDataSetChanged();
                //mAdapter.notifyItemRemoved(position);
                //mAdapter.notifyItemRangeChanged(position, mNiveis.size());


            } else {
                mNiveis.remove(position);
                mRecycler.removeViewAt(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mNiveis.size());
                mNiveis.add(nivel);
                mAdapter.notifyItemRangeRemoved(0, mNiveis.size() - 1);
                mAdapter.notifyItemRangeInserted(0, mNiveis.size());
                Snackbar.make(mRecycler, mContext.getResources().getString(R.string.cant_delete), Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }
}
