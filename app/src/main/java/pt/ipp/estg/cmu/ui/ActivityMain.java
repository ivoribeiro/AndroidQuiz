package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.DbHandler;
import pt.ipp.estg.cmu.db.repositories.JogadorRepo;
import pt.ipp.estg.cmu.models.Jogador;
import pt.ipp.estg.cmu.tasks.DownloadImage;


public class ActivityMain extends ActivityBase implements View.OnClickListener {

    private Button mBtStart;
    private TextView mWelcomeTxt;
    private TextView mUserNameTxt;
    private TextView mScoreTxt;
    private TextView mLevelTxt;


    private String mUserName;
    private int mPontos;
    private int mNivel;
    private Jogador jogador;
    private JogadorRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.repo = new JogadorRepo(this);

        new DbHandler(this, "androidQuiz.db");
        setContentView(R.layout.activity_main);

        this.jogador = this.repo.getById(1);
        //dummy data
        mPontos = jogador.getPontuacao();
        mNivel = 2;
        mUserName = jogador.getUsername();


        mBtStart = (Button) findViewById(R.id.bt_start_game);
        mUserNameTxt = (TextView) findViewById(R.id.user_text);
        mWelcomeTxt = (TextView) findViewById(R.id.welcome_user);
        mScoreTxt = (TextView) findViewById(R.id.txt_score);
        mLevelTxt = (TextView) findViewById(R.id.txt_level);

        mUserNameTxt.setText(getResources().getString(R.string.hello_text) + " " + mUserName);
        mWelcomeTxt.setText(getResources().getString(R.string.welcome_text));
        mScoreTxt.setText(getResources().getString(R.string.txt_score) + " : " + mPontos);
        mLevelTxt.setText(getResources().getString(R.string.txt_level) + " : " + mNivel);

        mBtStart.setOnClickListener(this);

        new DownloadImage(this, "tech", "nivel1", "1").execute("http://www.sftravel.com/sites/sftraveldev.prod.acquia-sites.com/files/SanFrancisco_0.jpg");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_game:
                this.startActivity(new Intent(this, CategoriaActivity.class));
                break;
        }
    }
}
