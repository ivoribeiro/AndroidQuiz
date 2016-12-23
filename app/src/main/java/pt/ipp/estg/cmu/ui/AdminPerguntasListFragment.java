package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterPerguntasList;
import pt.ipp.estg.cmu.callbacks.RecyclerSwipePerguntaTouchHelper;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.util.Util;


public class AdminPerguntasListFragment extends Fragment implements View.OnClickListener {

    private Nivel mNivel;
    private RecyclerView mRecycler;
    private PerguntaRepo mRepository;
    private AdapterPerguntasList mAdapter;
    private FloatingActionButton mFab;

    private ArrayList<Pergunta> mPerguntas;

    public AdminPerguntasListFragment() {
        // Required empty public constructor
    }

    public static AdminPerguntasListFragment newInstance(Nivel nivel) {
        AdminPerguntasListFragment fragment = new AdminPerguntasListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Util.ARG_LEVEL, nivel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNivel = getArguments().getParcelable(Util.ARG_LEVEL);
        }
        mRepository = new PerguntaRepo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_lista_perguntas, container, false);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPerguntas = mRepository.getAllByNivel(mNivel.getId());
        mAdapter = new AdapterPerguntasList(getContext(), mPerguntas);
        mRecycler.setAdapter(mAdapter);

        //swipe to remove
        RecyclerSwipePerguntaTouchHelper swipeTouch = new RecyclerSwipePerguntaTouchHelper(0, ItemTouchHelper.RIGHT, getContext(), mRecycler, mPerguntas, mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeTouch);
        itemTouchHelper.attachToRecyclerView(mRecycler);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            ((AppCompatActivity) getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, AdminNovaPerguntaFragment.newInstance(mNivel, null))
                    .addToBackStack(Util.STACK_ADMIN)
                    .commit();
        }
    }
}
