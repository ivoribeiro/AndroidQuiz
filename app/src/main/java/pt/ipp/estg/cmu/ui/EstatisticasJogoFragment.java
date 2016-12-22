package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.estatisticas.EstatisticasCategoria;
import pt.ipp.estg.cmu.estatisticas.EstatisticasJogo;
import pt.ipp.estg.cmu.estatisticas.EstatisticasNivel;
import pt.ipp.estg.cmu.estatisticas.EstatisticasPergunta;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class EstatisticasJogoFragment extends Fragment {

    private EstatisticasJogo mEstatisticasJogo;
    private EstatisticasCategoria mEstatisticasCategoria;
    private EstatisticasNivel mEstatisticasNivel;
    private EstatisticasPergunta mEstatisticasPergunta;

    //-------------------JOGO-----------------------
    private TextView nPontuacaoText;
    private TextView nPerguntasText;
    private TextView nRespostasCertasText;
    private TextView nRespostasErradasText;
    private TextView nAjudasUsadasText;
    private TextView nPontuacaoGanhaText;
    private TextView nPontuacaoPerdidaText;
    //---------------------------------------------

    public EstatisticasJogoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EstatisticasJogoFragment newInstance() {
        EstatisticasJogoFragment fragment = new EstatisticasJogoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEstatisticasJogo = new EstatisticasJogo(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas_jogo, container, false);
        //---------------------------------Jogo----------------------------------------
        nPontuacaoText = (TextView) view.findViewById(R.id.pontuacao_estatisticas_jogo);
        nPontuacaoText.setText("" + mEstatisticasJogo.getPontuacao());
        nPerguntasText = (TextView) view.findViewById(R.id.n_perguntas_estatisticas_jogo);
        nPerguntasText.setText("" + mEstatisticasJogo.getnPerguntas());
        nRespostasCertasText = (TextView) view.findViewById(R.id.n_respostas_certas_estatisticas_jogo);
        nRespostasCertasText.setText("" + mEstatisticasJogo.getnRespostasCertas());
        nRespostasErradasText = (TextView) view.findViewById(R.id.n_respostas_erradas_estatisticas_jogo);
        nRespostasErradasText.setText("" + mEstatisticasJogo.getnRespostasErradas());
        nAjudasUsadasText = (TextView) view.findViewById(R.id.n_ajudas_usadas_estatisticas_jogo);
        nAjudasUsadasText.setText("" + mEstatisticasJogo.getnAjudasUsadas());
        nPontuacaoGanhaText = (TextView) view.findViewById(R.id.pontuacao_ganha_estatisticas_jogo);
        nPontuacaoGanhaText.setText("" + mEstatisticasJogo.getPontuacaoGanha());
        nPontuacaoPerdidaText = (TextView) view.findViewById(R.id.pontuacao_perdida_estatisticas_jogo);
        nPontuacaoGanhaText.setText("" + mEstatisticasJogo.getPontuacaoPerdida());
        //-----------------------------------------------------------------------------


        // Inflate the layout for this fragment
        return view;
    }


}
