package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.enums.SoundEnum;
import pt.ipp.estg.cmu.helpers.MediaSoundsHelper;
import pt.ipp.estg.cmu.interfaces.GameInterfaceListener;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;
import pt.ipp.estg.dblib.models.Categoria;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;

/**
 * @author 8130031
 * @author 8130258
 */
public class GameActivity extends AppCompatActivity implements GameInterfaceListener, ViewPager.OnPageChangeListener {

    private static final int WAIT_MSECS = 2000;

    //layout
    private MediaSoundsHelper mSoundHelper;
    private ViewPager mViewPager;
    private TextView mLevelInfoText;
    private TextView mQuestionInfoText;
    private TextView mHintInfo;
    private TextView mScoreInfo;
    private Toolbar mToolbar;

    //data
    private Nivel mNivel;
    private Categoria mCategoria;

    private PerguntaRepo mRepository;
    private int nPerguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_game);
        mSoundHelper = new MediaSoundsHelper(this);

        mNivel = getIntent().getParcelableExtra(Util.ARG_LEVEL);
        mCategoria = getIntent().getParcelableExtra(Util.ARG_CATEGORIE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mCategoria.getNome());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mQuestionInfoText = (TextView) findViewById(R.id.question_info_text);
        mLevelInfoText = (TextView) findViewById(R.id.level_info_text);
        mHintInfo = (TextView) findViewById(R.id.hint_info);
        mScoreInfo = (TextView) findViewById(R.id.score_info);

        mLevelInfoText.setText(mNivel.getNumero());
        mHintInfo.setText(mNivel.getnAjudas() + " " + getResources().getString(R.string.ajudas_restantes));
        mScoreInfo.setText(mNivel.getPontuacao() + " " + getResources().getString(R.string.pontos_ganhos));

        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        mRepository = new PerguntaRepo(this.getApplicationContext());
        ArrayList<Pergunta> perguntas = mRepository.getAllByNivel(mNivel.getId());

        nPerguntas = perguntas.size();
        int i = 0;
        for (Pergunta pergunta : perguntas) {
            adapter.addFragment(GameFragment.newInstance(mNivel, pergunta));
            i++;
        }

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mQuestionInfoText.setText(position + 1 + "/" + nPerguntas);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setAnswered(final boolean hit) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_game_window_pop_up);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_view);
        if (hit) {
            mSoundHelper.play(SoundEnum.HIT);
            imageView.setBackground(getResources().getDrawable(R.drawable.img_correct));
        } else {
            mSoundHelper.play(SoundEnum.FAILED);
            imageView.setBackground(getResources().getDrawable(R.drawable.img_wrong));
        }
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                if (hit) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        }, WAIT_MSECS);
    }

    @Override
    public void unlock() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_game_window_pop_up);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_view);
        imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.img_unlock));
        dialog.show();
        mSoundHelper.play(SoundEnum.UNLOCK);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, WAIT_MSECS);
    }

    @Override
    public void setHint(int hints) {
        mHintInfo.setText(mNivel.getnAjudas() + " " + getResources().getString(R.string.ajudas_restantes));
    }

    @Override
    public void setScore(int score) {
        mScoreInfo.setText(score + " " + getResources().getString(R.string.pontos_ganhos));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
