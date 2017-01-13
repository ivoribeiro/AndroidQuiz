package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import pt.ipp.estg.cmu.enums.SoundEnum;
import pt.ipp.estg.cmu.helpers.MediaSoundsHelper;
import pt.ipp.estg.cmu.interfaces.GameInterfaceListener;
import pt.ipp.estg.cmu.util.StringsOperations;
import pt.ipp.estg.cmu.util.Util;
import pt.ipp.estg.cmu.util.UtilUI;
import pt.ipp.estg.dblib.estatisticas.EstatisticasNivel;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;


/**
 * @author 8130031
 * @author 8130258
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    private static final int ROW_1 = 4;
    private static final int ROW_2 = 9;
    private static final int ROW_3 = 14;

    private MediaSoundsHelper mMediaSoundHelper;
    private GameInterfaceListener mListener;
    private Nivel mNivel;
    private Pergunta mPergunta;
    private NivelRepo mNivelRepository;
    private PerguntaRepo mPerguntaRepository;
    private EstatisticasNivel mEstatisticasNivel;

    //layout
    private ViewGroup mViewGroup;
    private LinearLayout mAnswerLayout;
    private TableLayout mTableLayout;
    private ImageView mImageView;
    private ImageButton mResetButton;
    private ImageButton mHintButton;

    //resposta
    private String mCorrectRespostaConcat; // resposta certa sem espacos
    private String[] mRespostaUserArray; // array com cada letra que o user responde, # representa um espaco da resposta verdadeira
    private int mRespostaUserIndex; // posicao do mRespostaUserArray em que a resposta dada pelo user se encontra
    private int mRespostaUserSize;// tamanho atual da resposta dada pelo user


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
            mCorrectRespostaConcat = mPergunta.getRespostaCerta().replaceAll("\\s", "");
        }
        mMediaSoundHelper = new MediaSoundsHelper(getContext());
        mPerguntaRepository = new PerguntaRepo(this.getContext());
        mNivelRepository = new NivelRepo(this.getContext());
        mEstatisticasNivel = new EstatisticasNivel(this.getContext(), mNivel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewGroup = container;
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
                mMediaSoundHelper.play(SoundEnum.CLEAR);
                //update resposta atual
                mPergunta.setRespostaActual("");
                mPerguntaRepository.update(mPergunta);
                createLayout();
                break;
        }
    }

    private void createLayout() {
        mRespostaUserArray = StringsOperations.stringToArray(mPergunta.getRespostaCerta(), mPergunta.getRespostaActual());
        mRespostaUserIndex = StringsOperations.getIndexOfFirstSpace(mCorrectRespostaConcat.length(), mPergunta.getRespostaActual());
        mRespostaUserSize = StringsOperations.getNumberOfLetterInValue(mCorrectRespostaConcat.length(), mPergunta.getRespostaActual());
        buildLayoutGame();
        buildLayoutAnswer();
    }

    /**
     * Cria o layout relativo a resposta correta
     */
    private void buildLayoutAnswer() {
        mAnswerLayout.removeAllViews();
        int index = 0;
        for (int i = 0; i < mPergunta.getRespostaCerta().length(); ++i) {
            //invisible view to simulate empty space
            if (mPergunta.getRespostaCerta().charAt(i) == ' ') {
                mAnswerLayout.addView(UtilUI.newView(getActivity()));
            } else {
                //create resposta atual se existir
                char text = ' ';
                if (null != mRespostaUserArray[i]) {
                    text = mRespostaUserArray[i].charAt(0);
                }
                Button bt = UtilUI.newButton(getActivity(), text);
                bt.setId(i);
                mAnswerLayout.addView(bt);
            }
        }
    }

    /**
     * Cria o layout com os caracteres da string aletatoria
     */
    private void buildLayoutGame() {
        mTableLayout.removeAllViews();
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        for (int i = 0; i < mPergunta.getStringAleatoria().length(); ++i) {
            final Button bt_game = UtilUI.newButton(getActivity(), mPergunta.getStringAleatoria().charAt(i));
            //verificar se a pergunta foi respondida, para n deixar clicar caso tenha sido respondida
            if (!mPergunta.acertou()) {
                bt_game.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //verificar se a resposta esta completa
                        if (mRespostaUserSize < mCorrectRespostaConcat.length()) {
                            mMediaSoundHelper.play(SoundEnum.BUTTON_CLICK);

                            //verificar se esse botao ja nao tem uma ajuda ou um espaco(#)
                            if (mRespostaUserArray[mRespostaUserIndex] != null) {
                                while (mRespostaUserArray[mRespostaUserIndex] != null) {
                                    ++mRespostaUserIndex;
                                }
                            }
                            //obter o proximo botao da resposta a ser preenchido
                            Button bt_answer = (Button) mAnswerLayout.findViewById(mRespostaUserIndex);
                            if (bt_answer != null) {
                                bt_answer.setText(bt_game.getText());
                                bt_game.setVisibility(View.INVISIBLE);
                                mRespostaUserArray[mRespostaUserIndex] = bt_game.getText().toString();
                                ++mRespostaUserIndex;
                                ++mRespostaUserSize;

                                //update resposta atual
                                mPergunta.setRespostaActual(StringsOperations.arrayToString(mRespostaUserArray.length, mRespostaUserArray));
                                mPerguntaRepository.update(mPergunta);
                            }
                            checkIfIsFinished();
                        }
                    }
                });
            }
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
        if (mRespostaUserSize == mCorrectRespostaConcat.length()) {
            String value = StringsOperations.arrayToString(mRespostaUserArray.length, mRespostaUserArray);
            if (value.equals(mCorrectRespostaConcat)) {
                incrementPontosNivel();
                mListener.setAnswered(true);
                mPergunta.setAcertou(true);
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
        //tem ajudas?
        if (mNivel.getnAjudas() > 0) {
            mNivel.decrementnAjudas();
            decrementPontosHint();
            mPergunta.addAjudasUsadas();
            //update a bd
            mPerguntaRepository.update(mPergunta);
            mNivelRepository.update(mNivel);
            //actauliza as vistas para o numero de dicas disponiveis no nivel
            mListener.setHint(mNivel.getnAjudas());
        }
    }

    /**
     * Incrementa ao mNivel a pontuacão de uma resposta certa
     */
    private void incrementPontosNivel() {
        mNivel.addPontuacao(mNivel.getPontuacaoBase());
        mListener.setScore(mNivel.getPontuacao());
    }

    /**
     * Decrementa ao mNivel a pontuacão de uma resposta errada
     */
    private void decrementPontosNivel() {
        if (mNivel.getPontuacao() - mNivel.getPontuacaoBaseErrada() >= 0) {
            mNivel.removePontuacao(mNivel.getPontuacaoBaseErrada());
            mListener.setScore(mNivel.getPontuacao());
        }
    }

    private void decrementPontosHint() {
        if (mNivel.getPontuacao() - mNivel.getPontuacaoHint() >= 0) {
            mNivel.removePontuacao(mNivel.getPontuacaoHint());
            mListener.setScore(mNivel.getPontuacao());
        }
    }

    /**
     * preenche um botao aleatorio do layout da resposta com uma letra de ajuda
     */
    private void setHintLetter() {
        if (mNivel.getnAjudas() > 0) {
            Random random = new Random();
            int low = 0;
            int high = mPergunta.getRespostaCerta().length();
            int result;
            do {
                result = random.nextInt(high - low) + low;
            } while (mRespostaUserArray[result] != null);

            Button bt_answer = (Button) mAnswerLayout.findViewById(result);
            if (null != bt_answer) {
                mMediaSoundHelper.play(SoundEnum.HELP);
                char hint = mPergunta.getRespostaCerta().charAt(result);
                bt_answer.setText(hint + "");
                mRespostaUserArray[result] = hint + "";
                ++mRespostaUserSize;
                decrementAjuda();

                //update resposta atual
                mPergunta.setRespostaActual(StringsOperations.arrayToString(mRespostaUserArray.length, mRespostaUserArray));
                mPerguntaRepository.update(mPergunta);
            }
            checkIfIsFinished();
        } else {
            Snackbar.make(mViewGroup, getContext().getResources().getString(R.string.jogo_sem_ajudas_info), Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Desbloqueia o proximo nivel bloqueado da mesma categoria
     */
    private void unlockNextLevel() {
        if (mEstatisticasNivel.getnRespostasCertas() == mNivel.getnMinRespostasCertas()) {
            ArrayList<Nivel> niveisCategoria = mNivelRepository.getBloquadosByCategoria(mNivel.getCategoria());
            if (niveisCategoria.size() == 1) {
                Nivel aDesbloquear = niveisCategoria.get(0);
                aDesbloquear.setBloqueado(false);
                mNivelRepository.update(aDesbloquear);
                mListener.unlock();
            }
        }
    }
}
