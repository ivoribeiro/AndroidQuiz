package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterLevelList;
import pt.ipp.estg.cmu.callbacks.RecyclerSwipeNivelTouchHelper;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class LevelFragment extends Fragment implements View.OnClickListener {

    //layout
    private int NUM_COLUMNS = 1;
    private RecyclerView mRecyclerView;
    private boolean isAdmin;
    private FloatingActionButton mFab;

    //data
    private AdapterLevelList mAdapter;
    private Categoria mCategoria;
    private ArrayList<Nivel> mNiveis;
    private NivelRepo mRepository;

    public LevelFragment() {
    }

    public static LevelFragment newInstance(Categoria categoria, boolean isAdmin) {
        Bundle args = new Bundle();
        LevelFragment fragment = new LevelFragment();
        args.putParcelable(Util.ARG_CATEGORIE, categoria);
        args.putBoolean(Util.ARG_ADMIN, isAdmin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoria = getArguments().getParcelable(Util.ARG_CATEGORIE);
            isAdmin = getArguments().getBoolean(Util.ARG_ADMIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));

        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (isAdmin) {
            mFab.setOnClickListener(this);
        } else {
            mFab.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRepository = new NivelRepo(getContext());
        mNiveis = getAllNiveis();
        mAdapter = new AdapterLevelList(getActivity(), mNiveis, mCategoria, isAdmin);
        mRecyclerView.setAdapter(mAdapter);

        if (isAdmin) {    //swipe to remove
            RecyclerSwipeNivelTouchHelper swipeTouch = new RecyclerSwipeNivelTouchHelper(0, ItemTouchHelper.RIGHT, getContext(), mRecyclerView, mNiveis, mAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeTouch);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            //mFab.setVisibility(View.INVISIBLE);
            ((AppCompatActivity) getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, AdminNovoNivelFragment.newInstance(mCategoria, null))
                    .addToBackStack(Util.STACK_ADMIN)
                    .commit();
        }
    }

    private ArrayList<Nivel> getAllNiveis() {
        return mRepository.getAllByCategoria(mCategoria.getNome());
    }
}
