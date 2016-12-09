package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminFragmentsListener;
import pt.ipp.estg.cmu.models.Categoria;


public class AdminNovoNivelFragment extends Fragment {

    private static final String ARG_CATEGORIE = "CATEGORIA";

    private Categoria mCategoria;
    private AdminFragmentsListener mListener;

    public AdminNovoNivelFragment() {
        // Required empty public constructor
    }

    public static AdminNovoNivelFragment newInstance(Categoria categoria) {
        AdminNovoNivelFragment fragment = new AdminNovoNivelFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORIE, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoria = getArguments().getParcelable(ARG_CATEGORIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_novo_nivel, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
