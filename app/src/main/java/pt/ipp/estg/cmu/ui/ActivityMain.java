package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.CategoriaRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.estatisticas.EstatisticasJogo;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.services.RandQuestionService;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;


public class ActivityMain extends ActivityBase implements View.OnClickListener {

    //widget
    public static final String WIDGET_ACTION = "pt.ipp.estgf.cmu.MESSAGE";
    public static final String RAND_QUESTION_TIME = "rand_question_time";

    //layout
    private Button mBtStart;
    private TextView mWelcomeTxt;
    private TextView mUserNameTxt;
    private TextView mScoreTxt;

    private int mPontos;
    private CategoriaRepo mCategoriaRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_main);

        mNavigationView.setCheckedItem(R.id.nav_game);

        mCategoriaRepo = new CategoriaRepo(this);
        mPontos = new EstatisticasJogo(this).getPontuacao();

        mBtStart = (Button) findViewById(R.id.bt_start_game);
        mUserNameTxt = (TextView) findViewById(R.id.user_text);
        mWelcomeTxt = (TextView) findViewById(R.id.welcome_user);
        mScoreTxt = (TextView) findViewById(R.id.txt_score);

        mUserNameTxt.setText(getResources().getString(R.string.hello_text) + " " + mUserName);
        mWelcomeTxt.setText(getResources().getString(R.string.welcome_text));
        mScoreTxt.setText(getResources().getString(R.string.txt_score) + " : " + mPontos);

        mBtStart.setOnClickListener(this);



        Intent mIntent = new Intent(this, RandQuestionService.class);
        mIntent.putExtra(RAND_QUESTION_TIME, 1);
        startService(mIntent);
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
