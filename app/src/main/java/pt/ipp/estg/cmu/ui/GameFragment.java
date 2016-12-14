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
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;
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

    private ClickQuestionListener mListener;
    private Nivel mNivel;
    private Pergunta mPergunta;
    private NivelRepo mNivelRepo;
    private PerguntaRepo mPerguntaRepo;

    //layout
    private LinearLayout mAnswerLayout;
    private TableLayout mTableLayout;
    private ImageView mImageView;
    private ImageButton mResetButton;
    private ImageButton mHintButton;
    private TextView mHintInfo;
    private TextView mScoreInfo;

    //strings
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
        mPerguntaRepo = new PerguntaRepo(this.getContext());
        mNivelRepo = new NivelRepo(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        mAnswerLayout = (LinearLayout) view.findViewById(R.id.answer_layout);
        mTableLayout = (TableLayout) view.findViewById(R.id.game_table_layout);
        mResetButton = (ImageButton) view.findViewById(R.id.reset);
        mHintButton = (ImageButton) view.findViewById(R.id.hint);
        mHintInfo = (TextView) view.findViewById(R.id.hint_info);
        mScoreInfo = (TextView) view.findViewById(R.id.score_info);

        mHintButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

        if (mPergunta.acertou()) {
            mHintButton.setVisibility(View.GONE);
            mResetButton.setVisibility(View.GONE);
        }

        mImageView = (ImageView) view.findViewById(R.id.question_image);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHintInfo.setText(mNivel.getnAjudas() + " | Ajudas");
        mScoreInfo.setText(mNivel.getPontuacao() + " | Pontuação");

        Bitmap myBitmap = BitmapFactory.decodeFile(mPergunta.getImagem());
        mImageView.setImageBitmap(myBitmap);

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
                this.decrementarAjuda();
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
                this.incrementarPontosNivel();
                //muda o estado da mPergunta para acertou
                this.mPergunta.setAcertou(true);
                //adiciona resposta certa ao mNivel
                this.mNivel.addnRespostasCertas();
                //hint e reset buttons gone
                mHintButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.GONE);
                //save on bd
                this.mNivelRepo.updateNivel(this.mNivel);
                this.mPerguntaRepo.updatePergunta(this.mPergunta);
            } else {
                mListener.setAnswered(false);
                this.decrementPontosNivel();
                this.mPergunta.addRespostasErradas();
                createLayout();
                this.mNivelRepo.updateNivel(this.mNivel);
                this.mPerguntaRepo.updatePergunta(this.mPergunta);
            }
        }
    }


    /**
     * Decrementa do mNivel uma ajuda gasta
     * Actualiza o layout
     */
    private void decrementarAjuda() {
        if (this.mNivel.getnAjudas() > 0) {
            this.mNivel.decrementnAjudas();
            this.mHintInfo.setText("" + this.mNivel.getnAjudas() + " | Ajudas");
            this.mNivelRepo.updateNivel(this.mNivel);
        } else {
            //TODO mostrar mensagem
        }
    }

    /**
     * Atualiza a pontuação do mNivel na UI
     */
    private void updatePontuacao() {
        this.mScoreInfo.setText(this.mNivel.getPontuacao() + " | Pontuação");
    }

    /**
     * Incrementa ao mNivel a pontuacão de uma resposta certa
     */
    private void incrementarPontosNivel() {
        this.mNivel.addPontuacao(this.mNivel.getPontuacaoBase());
        this.updatePontuacao();
    }

    /**
     * Decrementa ao mNivel a pontuacão de uma resposta errada
     */
    private void decrementPontosNivel() {
        if (this.mNivel.getPontuacao() - this.mNivel.getPontuacaoBaseErrada() >= 0) {
            this.mNivel.removePontuacao(this.mNivel.getPontuacaoBaseErrada());
            this.updatePontuacao();
        }
    }


}
