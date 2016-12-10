package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;
import pt.ipp.estg.cmu.models.Categoria;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.util.Util;

public class GameActivity extends AppCompatActivity implements ClickQuestionListener, ViewPager.OnPageChangeListener {

    private static final int WAIT_MSECS = 2000;
    private int NUM_SWIPE_PAGES = 2;

    //layout
    private ViewPager mViewPager;
    private TextView mLevelInfoText;
    private TextView mQuestionInfoText;

    //data
    private Nivel mLevelInfo;
    private Categoria mCategoriaInfo;

    private PerguntaRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mLevelInfo = getIntent().getParcelableExtra(Util.ARG_LEVEL);
        mCategoriaInfo = getIntent().getParcelableExtra(Util.ARG_CATEGORIE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mCategoriaInfo.getNome());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mQuestionInfoText = (TextView) findViewById(R.id.question_info_text);
        mLevelInfoText = (TextView) findViewById(R.id.level_info_text);
        mLevelInfoText.setText(mLevelInfo.getNumero());

        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        this.repo = new PerguntaRepo(this.getApplicationContext());
        ArrayList<Pergunta> perguntas = this.repo.getAllByNivel(mLevelInfo.getId());
        int i = 0;
        for (Pergunta pergunta : perguntas) {
            adapter.addFragment(GameFragment.newInstance(i, mLevelInfo, pergunta));
            i++;
        }

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void setAnswered(boolean hit) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.window_pop_up);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image_view);
        if (hit) {
            imageView.setBackground(getResources().getDrawable(R.drawable.ic_check));
        } else {
            imageView.setBackground(getResources().getDrawable(R.drawable.ic_lock));
        }
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }, WAIT_MSECS);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mQuestionInfoText.setText(position + 1 + "/" + NUM_SWIPE_PAGES);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
