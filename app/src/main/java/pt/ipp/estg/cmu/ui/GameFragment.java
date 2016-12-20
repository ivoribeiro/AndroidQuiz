package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.interfaces.GameInterfaceListener;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.util.Util;
import pt.ipp.estg.cmu.util.UtilUI;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    private static final int ROW_1 = 4;
    private static final int ROW_2 = 9;
    private static final int ROW_3 = 14;

    private GameInterfaceListener mListener;
    private Nivel mNivel;
    private Pergunta mPergunta;
    private NivelRepo mNivelRepository;
    private PerguntaRepo mPerguntaRepository;

    //layout
    private LinearLayout mAnswerLayout;
    private TableLayout mTableLayout;
    private ImageView mImageView;
    private ImageButton mResetButton;
    private ImageButton mHintButton;

    //private TextView mHintInfo;
    //private TextView mScoreInfo;

    //resposta
    private String mCorrectAnswerConcat;
    private String mUserCorrectAnswer;
    private int mAtualCorrectIndex;

    public GameFragment() {
    }

    public static GameFragment newInstance(Nivel nivel, Pergunta pergunta) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putParcelable(Util.ARG_LEVEL, nivel);
        args.putParcelable(Util.ARG_QUESTION, pergunta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNivel = getArguments().getParcelable(Util.ARG_LEVEL);
            mPergunta = getArguments().getParcelable(Util.ARG_QUESTION);
            mCorrectAnswerConcat = mPergunta.getRespostaCerta().replaceAll("\\s", "");
        }
        mPerguntaRepository = new PerguntaRepo(this.getContext());
        mNivelRepository = new NivelRepo(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        mAnswerLayout = (LinearLayout) view.findViewById(R.id.answer_layout);
        mTableLayout = (TableLayout) view.findViewById(R.id.game_table_layout);
        mResetButton = (ImageButton) view.findViewById(R.id.reset);
        mHintButton = (ImageButton) view.findViewById(R.id.hint);
        mImageView = (ImageView) view.findViewById(R.id.question_image);

        mHintButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

        if (mPergunta.acertou()) {
            mHintButton.setVisibility(View.GONE);
            mResetButton.setVisibility(View.GONE);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bitmap bitmap = BitmapFactory.decodeFile(mPergunta.getImagem());
        mImageView.setImageBitmap(bitmap);

        createLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (GameActivity) context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hint:
                this.decrementAjuda();
                break;
            case R.id.reset:
                createLayout();
                break;
        }
    }

    private void createLayout() {
        buildLayoutGame();
        buildLayoutAnswer();
        mUserCorrectAnswer = "";
        mAtualCorrectIndex = 0;
    }

    /**
     * Cria o layout relativo a resposta correta
     */
    private void buildLayoutAnswer() {
        mAnswerLayout.removeAllViews();
        int index = 0;
        for (int i = 0; i < mPergunta.getRespostaCerta().length(); ++i) {
            //invisible button to simulate empty space
            if (mPergunta.getRespostaCerta().charAt(i) == ' ') {
                mAnswerLayout.addView(UtilUI.newView(getActivity()));
            } else {
                Button b = UtilUI.newButton(getActivity(), ' ');
                b.setId(index++);
                mAnswerLayout.addView(b);
            }
        }
    }

    /**
     * Cria o layout com os caracteres do jogo
     */
    private void buildLayoutGame() {
        mTableLayout.removeAllViews();
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        for (int i = 0; i < mPergunta.getStringAleatoria().length(); ++i) {
            final Button b = UtilUI.newButton(getActivity(), mPergunta.getStringAleatoria().charAt(i));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int indexChar = mCorrectAnswerConcat.indexOf(b.getText().toString());
                    Button b2 = (Button) mAnswerLayout.findViewById(mAtualCorrectIndex);
                    if (b2 != null) {
                        b2.setText(b.getText());
                        b.setVisibility(View.INVISIBLE);
                        ++mAtualCorrectIndex;
                        mUserCorrectAnswer = mUserCorrectAnswer + b.getText();
                        checkIfIsFinished();
                    }
                }
            });
            row.addView(b, params);
            if (i == ROW_1 || i == ROW_2 || i == ROW_3) {
                mTableLayout.addView(row);
                row = new TableRow(getActivity());
            }
        }
    }

    /**
     * Verifica se a resposta do jogador foi correta
     * adiciona pontuacao ao mNivel se respondeu corretamente
     * retira pontuacao ao mNivel se respondeu de forma errada
     */
    private void checkIfIsFinished() {
        if (mUserCorrectAnswer.length() == mCorrectAnswerConcat.length()) {
            if (mUserCorrectAnswer.equals(mCorrectAnswerConcat)) {
                mListener.setAnswered(true);
                //incrementa os pontos ganhos ao mNivel
                this.incrementPontosNivel();
                //muda o estado da mPergunta para acertou
                this.mPergunta.setAcertou(true);
                //adiciona resposta certa ao mNivel
                this.mNivel.addnRespostasCertas();
                //hint e reset buttons gone
                mHintButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.GONE);
                //save on bd
                this.mNivelRepository.updateNivel(this.mNivel);
                this.mPerguntaRepository.updatePergunta(this.mPergunta);
            } else {
                mListener.setAnswered(false);
                this.decrementPontosNivel();
                this.mPergunta.addRespostasErradas();
                createLayout();
                this.mNivelRepository.updateNivel(this.mNivel);
                this.mPerguntaRepository.updatePergunta(this.mPergunta);
            }
        }
    }


    /**
     * Decrementa do mNivel uma ajuda gasta
     * Actualiza o layout
     */
    private void decrementAjuda() {
        if (mNivel.getnAjudas() > 0) {
            mNivel.decrementnAjudas();
            mNivelRepository.updateNivel(mNivel);
            mListener.setHint(mNivel.getnAjudas());
        } else {
            //TODO mostrar mensagem
        }
    }

    /**
     * Incrementa ao mNivel a pontuacão de uma resposta certa
     */
    private void incrementPontosNivel() {
        mNivel.addPontuacao(mNivel.getPontuacaoBase());
        mListener.setScore(mNivel.getPontuacaoBase());

    }

    /**
     * Decrementa ao mNivel a pontuacão de uma resposta errada
     */
    private void decrementPontosNivel() {
        if (mNivel.getPontuacao() - mNivel.getPontuacaoBaseErrada() >= 0) {
            mNivel.removePontuacao(mNivel.getPontuacaoBaseErrada());
            mListener.setScore(mNivel.getPontuacaoBase());
        }
    }


}
