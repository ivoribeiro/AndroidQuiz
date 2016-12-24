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

import java.util.ArrayList;
import java.util.Random;

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

    //resposta
    private String mCorrectAnswerConcat;
    private String[] mUserAnswerArray;
    private int mUserAnswerIndex;

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

        mUserAnswerArray = new String[mCorrectAnswerConcat.length()];

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
                setHintLetter();
                break;
            case R.id.reset:
                createLayout();
                break;
        }
    }

    private void createLayout() {
        buildLayoutGame();
        buildLayoutAnswer();
        mUserAnswerArray = new String[mCorrectAnswerConcat.length()];
        mUserAnswerIndex = 0;
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
                Button bt = UtilUI.newButton(getActivity(), ' ');
                bt.setId(index++);
                mAnswerLayout.addView(bt);
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
            final Button bt_game = UtilUI.newButton(getActivity(), mPergunta.getStringAleatoria().charAt(i));
            bt_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button bt_answer = (Button) mAnswerLayout.findViewById(mUserAnswerIndex);
                    if (bt_answer != null) {
                        bt_answer.setText(bt_game.getText());
                        bt_game.setVisibility(View.INVISIBLE);
                        mUserAnswerArray[mUserAnswerIndex] = bt_game.getText().toString();//array com cada letra da resposta do user, preenchido a medida que vai respondendo
                        ++mUserAnswerIndex;
                        checkIfIsFinished();
                    }
                }
            });
            row.addView(bt_game, params);
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
        if (mUserAnswerIndex == mCorrectAnswerConcat.length()) {
            if (arrayToString().equals(mCorrectAnswerConcat)) {
                incrementPontosNivel();
                mListener.setAnswered(true);
                mPergunta.setAcertou(true);
                mNivel.addnRespostasCertas();
                mHintButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.GONE);
                //save on bd
                mNivelRepository.update(mNivel);
                mPerguntaRepository.update(mPergunta);
                unlockNextLevel();
            } else {
                decrementPontosNivel();
                mListener.setAnswered(false);
                mPergunta.addRespostasErradas();
                mNivelRepository.update(mNivel);
                mPerguntaRepository.update(mPergunta);
                createLayout();
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
            mNivelRepository.update(mNivel);
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


    private String arrayToString() {
        String result = "";
        for (int i = 0; i < mUserAnswerIndex; ++i) {
            result += mUserAnswerArray[i];
        }
        return result;
    }

    private void setHintLetter() {
        if (mNivel.getnAjudas() > 0) {
            Random random = new Random();
            int low = 0;
            int high = mCorrectAnswerConcat.length();
            int result;
            do {
                result = random.nextInt(high - low) + low;
            } while (null != mUserAnswerArray[result]);
            Button bt_answer = (Button) mAnswerLayout.findViewById(result);
            if (null != bt_answer) {
                char hint = mCorrectAnswerConcat.charAt(result);
                bt_answer.setText(hint + "");
                mUserAnswerArray[result] = hint + "";
                ++mUserAnswerIndex;
                checkIfIsFinished();
                decrementAjuda();
            }
        }
    }

    /**
     * Desbloqueia o proximo nivel bloqueado da mesma categoria
     */
    private void unlockNextLevel() {
        if (canUnlock()) {
            ArrayList<Nivel> niveisCategoria = mNivelRepository.getBloquadosByCategoria(this.mNivel.getCategoria());
            Nivel aDesbloquear = niveisCategoria.get(0);
            aDesbloquear.setBloqueado(false);
            this.mNivelRepository.update(aDesbloquear);
        }
    }

    /**
     * Verifica se pode desbloquear
     * so podemos desbloquear quando o numero de respostas certas é igual
     * ao numero de respostas minimas para desbloquear
     *
     * @return
     */
    private boolean canUnlock() {
        return this.mNivel.getnRespostasCertas() == this.mNivel.getnMinRespostasCertas();
    }
}
