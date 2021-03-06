package pt.ipp.estg.cmu.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.models.Categoria;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.cmu.util.Util;

/**
 * @author 8130031
 * @author 8130258
 */
public class AdminNovoNivelFragment extends Fragment implements View.OnClickListener {


    private Categoria mCategoria;

    private EditText mNome;
    private EditText mPontuacaoPergunta;
    private EditText mPontuacaoRetiradaErrada;
    private EditText mPontuacaoRetiradaAjuda;
    private EditText mNumMaxAjudas;
    private FloatingActionButton mFab;
    private CheckBox mBloqueado;
    private EditText mNumMinDesbloquear;

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

        if (savedInstanceState != null) {
            if (editMode) {
                mNivel = savedInstanceState.getParcelable(Util.ARG_LEVEL);
            } else {
                mCategoria = savedInstanceState.getParcelable(Util.ARG_CATEGORIE);
            }
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
        mBloqueado = (CheckBox) view.findViewById(R.id.level_block_text);
        mNumMinDesbloquear = (EditText) view.findViewById(R.id.level_min_num_text);

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
            mBloqueado.setChecked(mNivel.isBloqueado());
            mNumMinDesbloquear.setText("" + mNivel.getnMinRespostasCertas());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (editMode) {
            outState.putParcelable(Util.ARG_LEVEL, mNivel);
        } else {
            outState.putParcelable(Util.ARG_CATEGORIE, mCategoria);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            saveNivel();
        }
    }

    private void saveNivel() {
        String nome = mNome.getText().toString();
        String pontuacaoBase = mPontuacaoPergunta.getText().toString();
        String pontuacaoBaseErradas = mPontuacaoRetiradaErrada.getText().toString();
        String pontuacaoHint = mPontuacaoRetiradaAjuda.getText().toString();
        String nAjudas = mNumMaxAjudas.getText().toString();
        boolean bloqueado = mBloqueado.isChecked();
        String nMinDesbloquear = mNumMinDesbloquear.getText().toString();

        if (!nMinDesbloquear.equals("") && !nome.equals("") && !pontuacaoBase.equals("") && !pontuacaoBaseErradas.equals("") && !pontuacaoHint.equals("") && !nAjudas.equals("")) {

            Nivel level = new Nivel();
            level.setNumero(nome);
            level.setPontuacaoBase(Integer.parseInt(pontuacaoBase));
            level.setPontuacaoBaseErrada(Integer.parseInt(pontuacaoBaseErradas));
            level.setPontuacaoHint(Integer.parseInt(pontuacaoHint));
            level.setnAjudas(Integer.parseInt(nAjudas));
            level.setBloqueado(bloqueado);
            level.setnMinRespostasCertas(Integer.parseInt(nMinDesbloquear));

            if (!editMode) {
                level.setCategoria(mCategoria.getNome());
                mNivelRepo.insertInto(level);
            } else {
                level.setId(mNivel.getId());
                level.setCategoria(mNivel.getCategoria());
                level.setPontuacao(mNivel.getPontuacao());
                mNivelRepo.update(level);
            }

            getActivity().getSupportFragmentManager().popBackStack(Util.STACK_ADMIN, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.admin_toast_campos_erro), Toast.LENGTH_SHORT).show();
        }
    }
}
