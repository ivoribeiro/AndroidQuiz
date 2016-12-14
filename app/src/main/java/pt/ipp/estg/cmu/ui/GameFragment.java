package pt.ipp.estg.cmu.ui;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Random;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.util.UtilUI;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    private static final int ROW_1 = 4;
    private static final int ROW_2 = 9;
    private static final int ROW_3 = 14;

    private ClickQuestionListener mListener;
    private int index;
    private Nivel nivel;
    private Pergunta pergunta;
    private NivelRepo mNivelRepo;
    private PerguntaRepo mPerguntaRepo;

    //layout
    private static int NUMBER_GAME_BUTTONS = 15;
    private LinearLayout mAnswerLayout;
    private TableLayout mTableLayout;
    private ImageView mImageView;
    private ImageButton mResetButton;
    private ImageButton mHintButton;
    private TextView mHintInfo;
    private TextView mScoreInfo;


    //strings
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String mCorrectAnswer;
    private String mCorrectAnswerConcat;
    private String mRandomGameString;
    private String mUserCorrectAnswer;

    private int mCorrectAnswerSize;
    private int mAtualCorrectIndex;

    public GameFragment() {
    }

    public static GameFragment newInstance(int index, Nivel nivel, Pergunta pergunta) {
        Bundle args = new Bundle();
        args.putInt("INDEX", index);
        args.putParcelable("NIVEL", nivel);
        args.putParcelable("PERGUNTA", pergunta);
        args.putString("CORRECT", pergunta.getRespostaCerta());
        args.putString("CONCAT", pergunta.getRespostaCerta().replaceAll("\\s", ""));
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPerguntaRepo=new PerguntaRepo(this.getContext());
        mNivelRepo=new NivelRepo(this.getContext());
        mCorrectAnswer = getArguments().getString("CORRECT");
        mCorrectAnswerConcat = getArguments().getString("CONCAT");
        index = getArguments().getInt("INDEX");
        nivel = getArguments().getParcelable("NIVEL");
        pergunta = getArguments().getParcelable("PERGUNTA");

        mUserCorrectAnswer = "";
        mAtualCorrectIndex = 0;
        //tamanho da resposta sem espaços
        mCorrectAnswerSize = mCorrectAnswerConcat.length();
        //todas as letras do alfabeto menos as da resposta
        String abc = removeStringFromString(mCorrectAnswerConcat, alphabet);
        //numero de catacteres que vao ser gerados aleatoriamente
        int saltSize = NUMBER_GAME_BUTTONS - mCorrectAnswerSize;
        //letras aleatorias
        String saltString = generateString(abc, saltSize);
        //letras aleatorias mais letras da resposta
        saltString = saltString + mCorrectAnswerConcat;
        //baralhar letras
        mRandomGameString = generateString(saltString, NUMBER_GAME_BUTTONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        mAnswerLayout = (LinearLayout) view.findViewById(R.id.answer_layout);
        mTableLayout = (TableLayout) view.findViewById(R.id.game_table_layout);
        mResetButton = (ImageButton) view.findViewById(R.id.reset);
        mHintButton = (ImageButton) view.findViewById(R.id.hint);
        mHintInfo = (TextView) view.findViewById(R.id.hint_info);
        mHintInfo.setText(nivel.getnAjudas() + " | Ajudas");
        mScoreInfo = (TextView) view.findViewById(R.id.score_info);
        mScoreInfo.setText(nivel.getPontuacao() + " | Pontuação");

        mHintButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

        if(this.pergunta.acertou()){
            mHintButton.setVisibility(View.GONE);
            mResetButton.setVisibility(View.GONE);
        }

        mImageView = (ImageView) view.findViewById(R.id.question_image);
        switch (index) {
            case 0:
                mImageView.setBackground(getActivity().getResources().getDrawable(R.drawable.question_1));
                break;
            case 1:
                mImageView.setBackground(getActivity().getResources().getDrawable(R.drawable.question_2));
                break;
        }

        createLayout();
        return view;
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

    private void buildLayoutAnswer() {
        mAnswerLayout.removeAllViews();
        int index = 0;
        for (int i = 0; i < mCorrectAnswer.length(); ++i) {
            //invisible button to simulate empty space
            if (mCorrectAnswer.charAt(i) == ' ') {
                mAnswerLayout.addView(UtilUI.newView(getActivity()));
            } else {
                Button b = UtilUI.newButton(getActivity(), ' ');
                b.setId(index++);
                mAnswerLayout.addView(b);
            }
        }
    }

    private void buildLayoutGame() {
        mTableLayout.removeAllViews();
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        for (int i = 0; i < mRandomGameString.length(); ++i) {
            final Button b = UtilUI.newButton(getActivity(), mRandomGameString.charAt(i));
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
     * adiciona pontuacao ao nivel se respondeu corretamente
     * retira pontuacao ao nivel se respondeu de forma errada
     */
    private void checkIfIsFinished() {
        if (mUserCorrectAnswer.length() == mCorrectAnswerConcat.length()) {
            if (mUserCorrectAnswer.equals(mCorrectAnswerConcat)) {
                mListener.setAnswered(true);
                //incrementa os pontos ganhos ao nivel
                this.incrementarPontosNivel();
                //muda o estado da pergunta para acertou
                this.pergunta.setAcertou(true);
                //adiciona resposta certa ao nivel
                this.nivel.addnRespostasCertas();
                //hint e reset buttons gone
                mHintButton.setVisibility(View.GONE);
                mResetButton.setVisibility(View.GONE);
                //save on bd
                this.mNivelRepo.updateNivel(this.nivel);
                this.mPerguntaRepo.updatePergunta(this.pergunta);
            } else {
                mListener.setAnswered(false);
                this.decrementPontosNivel();
                this.pergunta.addRespostasErradas();
                createLayout();
                this.mNivelRepo.updateNivel(this.nivel);
                this.mPerguntaRepo.updatePergunta(this.pergunta);



            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////// STRINGS MANIPULATION
    private static String generateString(String characters, int length) {
        Random rng = new Random();
        ArrayList<Character> result = new ArrayList<>();
        while (result.size() < length) {
            char ch = characters.charAt(rng.nextInt(characters.length()));
            if (!result.contains(ch)) {
                result.add(ch);
            }
        }
        String text = "";
        for (int i = 0; i < result.size(); ++i) {
            text = text + result.get(i);
        }
        return text;
    }

    private String removeStringFromString(String answer, String abc) {
        for (int i = 0; i < answer.length(); ++i) {
            if (abc.contains(answer.charAt(i) + "")) {
                abc = deleteCharAt(abc, abc.indexOf(answer.charAt(i)));
            }
        }
        return abc;
    }

    private static String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1);
    }

    /**
     * Decrementa do nivel uma ajuda gasta
     * Actualiza o layout
     */
    private void decrementarAjuda() {
        if (this.nivel.getnAjudas() > 0) {
            this.nivel.decrementnAjudas();
            this.mHintInfo.setText("" + this.nivel.getnAjudas() + " | Ajudas");
            this.mNivelRepo.updateNivel(this.nivel);
        } else {
            //TODO mostrar mensagem
        }
    }

    /**
     * Atualiza a pontuação do nivel na UI
     */
    private void updatePontuacao() {
        this.mScoreInfo.setText(this.nivel.getPontuacao() + " | Pontuação");
    }

    /**
     * Incrementa ao nivel a pontuacão de uma resposta certa
     */
    private void incrementarPontosNivel() {
        this.nivel.addPontuacao(this.nivel.getPontuacaoBase());
        this.updatePontuacao();
    }

    /**
     * Decrementa ao nivel a pontuacão de uma resposta errada
     */
    private void decrementPontosNivel() {
        if (this.nivel.getPontuacao() - this.nivel.getPontuacaoBaseErrada() >= 0) {
            this.nivel.removePontuacao(this.nivel.getPontuacaoBaseErrada());
            this.updatePontuacao();
        }
    }




}
