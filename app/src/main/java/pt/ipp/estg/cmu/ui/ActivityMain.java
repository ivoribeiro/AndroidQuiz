package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.DbHandler;
import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.db.repositories.JogadorRepo;
import pt.ipp.estg.cmu.util.Util;


public class ActivityMain extends ActivityBase implements View.OnClickListener {

    //widget
    public static final String INTENT_MESSAGE = "pt.ipp.estgf.cmu.widgetproject.MESSAGE";
    public static final String INTENT_MESSAGE_EXTRA = "message_extra";

    //layout
    private Button mBtStart;
    private TextView mWelcomeTxt;
    private TextView mUserNameTxt;
    private TextView mScoreTxt;

    private String mUserName;
    private int mPontos;
    private CategoriaRepo mCategoriaRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoriaRepo=new CategoriaRepo(this);

        mPontos = mCategoriaRepo.getPontuacaoJogo();
        mUserName = mJogador.getUsername();


        mBtStart = (Button) findViewById(R.id.bt_start_game);
        mUserNameTxt = (TextView) findViewById(R.id.user_text);
        mWelcomeTxt = (TextView) findViewById(R.id.welcome_user);
        mScoreTxt = (TextView) findViewById(R.id.txt_score);

        mUserNameTxt.setText(getResources().getString(R.string.hello_text) + " " + mUserName);
        mWelcomeTxt.setText(getResources().getString(R.string.welcome_text));
        //TODO fazer query para somar todas as pontuacoes de todos os niveis ativos ou ir actualizando pontuacao do jogo
        mScoreTxt.setText(getResources().getString(R.string.txt_score) + " : " + mPontos);

        mBtStart.setOnClickListener(this);

        //new DownloadImage(this, "tech", "nivel1", "1").execute("http://www.sftravel.com/sites/sftraveldev.prod.acquia-sites.com/files/SanFrancisco_0.jpg");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_game:
                this.startActivity(new Intent(this, CategoriaActivity.class)
                        .putExtra(Util.ARG_ADMIN, false));
                break;
        }
    }
}
