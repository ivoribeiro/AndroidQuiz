package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminFragmentsListener;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;


public class AdminNovoNivelFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_CATEGORIE = "CATEGORIA";

    private Categoria mCategoria;
    private AdminFragmentsListener mListener;

    private EditText mNome;
    private EditText mPontuacaoPergunta;
    private EditText mPontuacaoRetiradaErrada;
    private EditText mPontuacaoRetiradaAjuda;
    private EditText mNumMaxAjudas;
    private FloatingActionButton mFab;

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
        mNome = (EditText) view.findViewById(R.id.level_title_text);
        mPontuacaoPergunta = (EditText) view.findViewById(R.id.level_pontuacao_base_text);
        mPontuacaoRetiradaErrada = (EditText) view.findViewById(R.id.level_errada_text);
        mPontuacaoRetiradaAjuda = (EditText) view.findViewById(R.id.level_ajuda_text);
        mNumMaxAjudas = (EditText) view.findViewById(R.id.level_ajuda_num_text);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);

        mFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            Nivel level = new Nivel();
            level.setCategoria(mCategoria.getNome());
            level.setNumero(mNome.getText().toString());
            //level.setPontuacaoBase(mPontuacaoPergunta.getText().toString());
            //level.setPontuacaoBaseErrada(mPontuacaoRetiradaErrada.getText().toString());
            //level.setPontuacaoHint(mPontuacaoRetiradaAjuda.getText().toString());

            //TODO save level on db
        }
    }
}
