package pt.ipp.estg.cmu.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminFragmentsListener;
import pt.ipp.estg.cmu.models.Categoria;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminNovoNivelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdminFragmentsListener) {
            mListener = (AdminFragmentsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
