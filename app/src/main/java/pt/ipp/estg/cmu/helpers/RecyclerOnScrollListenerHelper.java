package pt.ipp.estg.cmu.helpers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

/**
 * Created by navega on 1/5/17.
 */

public class RecyclerOnScrollListenerHelper extends RecyclerView.OnScrollListener {

    private FloatingActionButton mFab;

    public RecyclerOnScrollListenerHelper(FloatingActionButton mFab) {
        this.mFab = mFab;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) {
            // Scroll Down
            if (mFab.isShown()) {
                mFab.hide();
            }
        } else if (dy < 0) {
            // Scroll Up
            if (!mFab.isShown()) {
                mFab.show();
            }
        }
    }
}
