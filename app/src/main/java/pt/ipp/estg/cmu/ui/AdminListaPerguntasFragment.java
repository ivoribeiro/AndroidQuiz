package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.models.Nivel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminListaPerguntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminListaPerguntasFragment extends Fragment {

    private static final String ARG_NIVEL = "NIVEL";

    private Nivel mNivel;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_lista_perguntas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
