package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * Use the {@link EstatisticasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstatisticasFragment extends Fragment {

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

    public EstatisticasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EstatisticasFragment.
     */
    public static EstatisticasFragment newInstance() {
        EstatisticasFragment fragment = new EstatisticasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mEstatisticasPergunta = new EstatisticasPergunta(context);
        //mEstatisticasNivel = new EstatisticasNivel(context, 1);
        //mEstatisticasCategoria = new EstatisticasCategoria(context);
        //TODO FIX THIS SHIT MODAFOCA
        mEstatisticasJogo = new EstatisticasJogo(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas, container, false);
        //---------------------------------Jogo----------------------------------------
        nPontuacaoText = (TextView) view.findViewById(R.id.pontuacao_estatisticas_jogo);
        nPontuacaoText.setText(""+mEstatisticasJogo.getPontuacao());
        nPerguntasText = (TextView) view.findViewById(R.id.n_perguntas_estatisticas_jogo);
        nPerguntasText.setText(""+mEstatisticasJogo.getnPerguntas());
        nRespostasCertasText = (TextView) view.findViewById(R.id.n_respostas_certas_estatisticas_jogo);
        nRespostasCertasText.setText(""+mEstatisticasJogo.getnRespostasCertas());
        nRespostasErradasText = (TextView) view.findViewById(R.id.n_respostas_erradas_estatisticas_jogo);
        nRespostasErradasText.setText(""+mEstatisticasJogo.getnRespostasErradas());
        nAjudasUsadasText = (TextView) view.findViewById(R.id.n_ajudas_usadas_estatisticas_jogo);
        nAjudasUsadasText.setText(""+mEstatisticasJogo.getnAjudasUsadas());
        nPontuacaoGanhaText = (TextView) view.findViewById(R.id.pontuacao_ganha_estatisticas_jogo);
        nPontuacaoGanhaText.setText(""+mEstatisticasJogo.getPontuacaoGanha());
        nPontuacaoPerdidaText = (TextView) view.findViewById(R.id.pontuacao_perdida_estatisticas_jogo);
        nPontuacaoGanhaText.setText(""+mEstatisticasJogo.getPontuacaoPerdida());
        //-----------------------------------------------------------------------------


        // Inflate the layout for this fragment
        return view;
    }


}
