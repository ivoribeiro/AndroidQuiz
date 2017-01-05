package pt.ipp.estg.cmu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.dblib.estatisticas.EstatisticasJogo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class EstatisticasJogoFragment extends Fragment {

    private EstatisticasJogo mEstatisticasJogo;


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
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEstatisticasJogo = new EstatisticasJogo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas_jogo, container, false);

        //---------------------------------Jogo----------------------------------------
        nPontuacaoText = (TextView) view.findViewById(R.id.pontuacao_jogo);
        nPerguntasText = (TextView) view.findViewById(R.id.n_perguntas_jogo);
        nRespostasCertasText = (TextView) view.findViewById(R.id.n_respostas_certas_jogo);
        nRespostasErradasText = (TextView) view.findViewById(R.id.n_respostas_erradas_jogo);
        nAjudasUsadasText = (TextView) view.findViewById(R.id.n_ajudas_usadas_jogo);
        nPontuacaoGanhaText = (TextView) view.findViewById(R.id.pontuacao_ganha_jogo);
        nPontuacaoPerdidaText = (TextView) view.findViewById(R.id.pontuacao_perdida_jogo);
        //-----------------------------------------------------------------------------

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nPontuacaoText.setText("" + mEstatisticasJogo.getPontuacao());
        nPerguntasText.setText("" + mEstatisticasJogo.getnPerguntas());
        nRespostasCertasText.setText("" + mEstatisticasJogo.getnRespostasCertas());
        nRespostasErradasText.setText("" + mEstatisticasJogo.getnRespostasErradas());
        nAjudasUsadasText.setText("" + mEstatisticasJogo.getnAjudasUsadas());
        nPontuacaoGanhaText.setText("" + mEstatisticasJogo.getPontuacaoGanha());
        nPontuacaoGanhaText.setText("" + mEstatisticasJogo.getPontuacaoPerdida());
    }
}
