package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterPerguntasList;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminListaPerguntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminListaPerguntasFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_NIVEL = "NIVEL";

    private Nivel mNivel;
    private RecyclerView mRecycler;
    private PerguntaRepo mRepository;
    private AdapterPerguntasList mAdapter;
    private FloatingActionButton mFab;

    public AdminListaPerguntasFragment() {
        // Required empty public constructor
    }

    public static AdminListaPerguntasFragment newInstance(Nivel nivel) {
        AdminListaPerguntasFragment fragment = new AdminListaPerguntasFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NIVEL, nivel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNivel = getArguments().getParcelable(ARG_NIVEL);
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

        mAdapter = new AdapterPerguntasList(getContext(), mRepository.getAllByNivel(mNivel.getId()));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            ((AppCompatActivity) getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, AdminNovaPerguntaFragment.newInstance(mNivel))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
