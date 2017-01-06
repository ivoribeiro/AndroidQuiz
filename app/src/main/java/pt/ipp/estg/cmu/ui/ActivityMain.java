package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.services.RandQuestionService;
import pt.ipp.estg.dblib.repositories.CategoriaRepo;
import pt.ipp.estg.dblib.estatisticas.EstatisticasJogo;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;


public class ActivityMain extends ActivityBase implements View.OnClickListener {

    //widget
    public static final String WIDGET_ACTION = "pt.ipp.estg.cmu.MESSAGE";
    public static final String RAND_QUESTION_TIME = "rand_question_time";
    public static final String WANT_NOTIFICATIONS = "want_notifications";

    //layout
    private Button mBtStart;
    private TextView mWelcomeTxt;
    private TextView mUserNameTxt;
    private TextView mScoreTxt;

    private int mPontos;

    private PreferencesSettings mPreferenceSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferenceSettings = new PreferencesSettings(this);
        setTheme(mPreferenceSettings.getThemePreference());
        setContentView(R.layout.activity_main);

        mNavigationView.setCheckedItem(R.id.nav_game);

        EstatisticasJogo estatisticasJogo = new EstatisticasJogo(this);
        mPontos = estatisticasJogo.getPontuacao();

        mBtStart = (Button) findViewById(R.id.bt_start_game);
        mUserNameTxt = (TextView) findViewById(R.id.user_text);
        mWelcomeTxt = (TextView) findViewById(R.id.welcome_user);
        mScoreTxt = (TextView) findViewById(R.id.txt_score);

        mUserNameTxt.setText(getResources().getString(R.string.hello_text) + " " + mUserName);
        mWelcomeTxt.setText(getResources().getString(R.string.welcome_text));
        mScoreTxt.setText(getResources().getString(R.string.txt_score) + " : " + mPontos);

        mBtStart.setOnClickListener(this);

        int minutos = mPreferenceSettings.getFrequenciaUpdate();
        boolean notifications = mPreferenceSettings.wantNotifications();


        Intent mIntent = new Intent(this, RandQuestionService.class);
        mIntent.putExtra(RAND_QUESTION_TIME, 1);
        mIntent.putExtra(WANT_NOTIFICATIONS, notifications);
        if (estatisticasJogo.getnPerguntasPorResponder() > 0) {
            startService(mIntent);
        }

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
