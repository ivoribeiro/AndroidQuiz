package pt.ipp.estg.cmu.callbacks;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;

import pt.ipp.estg.cmu.adapters.AdapterCategoriaGrid;
import pt.ipp.estg.cmu.models.Categoria;

public class RecyclerSwipeTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ArrayList<Categoria> mCategorias;
    private RecyclerView mRecycler;
    private Context mContext;

    private AdapterCategoriaGrid mAdapter;

    public RecyclerSwipeTouchHelper(int dragDirs, int swipeDirs, Context context, RecyclerView recyclerView, ArrayList<Categoria> categorias, AdapterCategoriaGrid adapter) {
        super(dragDirs, swipeDirs);
        this.mContext = context;
        this.mRecycler = recyclerView;
        this.mCategorias = categorias;
        this.mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();

        final Categoria categoria = mCategorias.get(position);

        Snackbar.make(mRecycler,
                "Deleted " + mCategorias.get(viewHolder.getAdapterPosition()).getNome(),
                Snackbar.LENGTH_LONG).setAction("Undo",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        mCategorias.add(categoria);
                        mAdapter.notifyItemRangeRemoved(0, mCategorias.size() - 1);

                        mAdapter.notifyItemRangeInserted(0, mCategorias.size());
                        //mAdapter.notifyDataSetChanged();
                        //mRecycler.setAdapter(new AdapterCategoriaGrid(mContext, mCategorias, true));


                    }
                }).show();
        mCategorias.remove(position);
        mRecycler.removeViewAt(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, mCategorias.size());

    }


}
