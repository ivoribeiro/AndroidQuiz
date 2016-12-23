package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.util.Util;


public class AdminNovoNivelFragment extends Fragment implements View.OnClickListener {


    private Categoria mCategoria;

    private EditText mNome;
    private EditText mPontuacaoPergunta;
    private EditText mPontuacaoRetiradaErrada;
    private EditText mPontuacaoRetiradaAjuda;
    private EditText mNumMaxAjudas;
    private FloatingActionButton mFab;
    private boolean editMode;
    private Nivel mNivel;
    private NivelRepo mNivelRepo;

    public AdminNovoNivelFragment() {
        // Required empty public constructor
    }

    public static AdminNovoNivelFragment newInstance(Categoria categoria, Nivel nivel) {
        AdminNovoNivelFragment fragment = new AdminNovoNivelFragment();
        Bundle args = new Bundle();
        if (categoria != null) {
            args.putParcelable(Util.ARG_CATEGORIE, categoria);
        } else if (nivel != null) {
            args.putParcelable(Util.ARG_LEVEL, nivel);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments().getParcelable(Util.ARG_CATEGORIE)) {
            mCategoria = getArguments().getParcelable(Util.ARG_CATEGORIE);
            editMode = false;
        } else if (null != getArguments().getParcelable(Util.ARG_LEVEL)) {
            mNivel = getArguments().getParcelable(Util.ARG_LEVEL);
            editMode = true;
        }
        mNivelRepo = new NivelRepo(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_novo_nivel, container, false);
        mNome = (EditText) view.findViewById(R.id.level_title_text);
        mPontuacaoPergunta = (EditText) view.findViewById(R.id.level_pontuacao_base_text);
        mPontuacaoRetiradaErrada = (EditText) view.findViewById(R.id.level_errada_text);
        mPontuacaoRetiradaAjuda = (EditText) view.findViewById(R.id.level_ajuda_text);
        mNumMaxAjudas = (EditText) view.findViewById(R.id.level_ajuda_num_text);
        //TODO novos campos
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (editMode) {
            mNome.setText(mNivel.getNumero());
            mPontuacaoPergunta.setText("" + mNivel.getPontuacaoBase());
            mPontuacaoRetiradaErrada.setText("" + mNivel.getPontuacaoBaseErrada());
            mPontuacaoRetiradaAjuda.setText("" + mNivel.getPontuacaoHint());
            mNumMaxAjudas.setText("" + mNivel.getnAjudas());
            //TODO novos campos
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            String nome = mNome.getText().toString();
            String pontuacaoBase = mPontuacaoPergunta.getText().toString();
            String pontuacaoBaseErradas = mPontuacaoRetiradaErrada.getText().toString();
            String pontuacaoHint = mPontuacaoRetiradaAjuda.getText().toString();
            String nAjudas = mNumMaxAjudas.getText().toString();
            if (!nome.equals("") && !pontuacaoBase.equals("") && !pontuacaoBaseErradas.equals("") && !pontuacaoHint.equals("") && !nAjudas.equals("")) {

                Nivel level = new Nivel();
                level.setNumero(nome);
                level.setPontuacaoBase(Integer.parseInt(pontuacaoBase));
                level.setPontuacaoBaseErrada(Integer.parseInt(pontuacaoBaseErradas));
                level.setPontuacaoHint(Integer.parseInt(pontuacaoHint));
                level.setnAjudas(Integer.parseInt(nAjudas));
                //TODO FIX
                level.setnMinRespostasCertas(10);
                //TODO FIX
                level.setBloqueado(false);

                //TODO save level on db

                if (!editMode) {
                    level.setCategoria(mCategoria.getNome());
                    mNivelRepo.insertInto(level);
                } else {
                    level.setId(mNivel.getId());
                    level.setnPerguntas(mNivel.getnPerguntas());
                    level.setPontuacao(mNivel.getPontuacao());
                    level.setnRespostasCertas(mNivel.getnRespostasCertas());
                    mNivelRepo.updateNivel(level);
                }

                getActivity().getSupportFragmentManager().popBackStack(Util.STACK_ADMIN, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.admin_toast_campos_erro), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
