package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;

public class ActivityQuestion extends AppCompatActivity implements ClickQuestionListener {

    private static final int WAIT_MSECS = 2000;

    //layout
    private ViewPager mViewPager;
    private TextView mHintInfoText;
    private TextView mScoreInfoText;
    private TextView mQuestionInfoText;

    //data
    private String mHintInfo;
    private String mScoreInfo;
    private String mQuestionInfo;
    private String mLevelInfo;
    private String mCategoriaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getDummyData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mCategoriaInfo);
        toolbar.setSubtitle(getResources().getString(R.string.txt_level) + " " + mLevelInfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHintInfoText = (TextView) findViewById(R.id.hint_info_text);
        mScoreInfoText = (TextView) findViewById(R.id.score_info_text);
        mQuestionInfoText = (TextView) findViewById(R.id.question_info_text);

        mHintInfoText.setText(mHintInfo + " " + getResources().getString(R.string.ajudas_restantes));
        mScoreInfoText.setText(mScoreInfo + " " + getResources().getString(R.string.txt_score));
        mQuestionInfoText.setText(mQuestionInfo);

        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addFragment(ActivityQuestionFragment.newInstance(0, "NEW YORK", "NEWYORK"));
        adapter.addFragment(ActivityQuestionFragment.newInstance(1, "EVORA", "EVORA"));

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);
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

    public void getDummyData() {
        mHintInfo = "3";
        mScoreInfo = "30";
        mQuestionInfo = "3/10";
        mLevelInfo = "3";
        mCategoriaInfo = "Tech";

    }
}
