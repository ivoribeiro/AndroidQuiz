package pt.ipp.estg.cmu.setup;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterViewPager;
import pt.ipp.estg.cmu.enums.RequestTypeEnum;
import pt.ipp.estg.cmu.interfaces.AdapterPageSetupCallback;
import pt.ipp.estg.cmu.security.SecurityAsyncTask;
import pt.ipp.estg.cmu.server.JsonBuilder;
import pt.ipp.estg.cmu.server.Request;
import pt.ipp.estg.cmu.ui.ActivityMain;
import pt.ipp.estg.cmu.util.Util;

/**
 * @author 8130031
 * @author 8130258
 */
public class PageSetupActivity extends AppCompatActivity implements AdapterPageSetupCallback, ViewPager.OnPageChangeListener, View.OnClickListener {

    //layout
    private AdapterViewPager mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageButton mNextBtn;
    private Button mSkipBtn, mFinishBtn;

    //data
    private int color0, color1, color2, color3, color4, color5;
    private int[] colorList = new int[6];

    private final ArgbEvaluator evaluator = new ArgbEvaluator();

    private ImageView zero, one, two, three, four, five;
    private ImageView[] indicators;

    private int page = 0;//  to track page position
    private static int avatarPage = 4;
    private static int adminConfigPage = 5;
    private PreferencesSetup mPreferencesSetup;

    //admin layout
    private boolean isEditTextNickNameNull;
    private boolean isEditTextPinNull;
    private boolean isAvatarPicked;
    private String chosenPin;

    //server data
    private int pickedAvatar;
    private String pickedNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        }
        setContentView(R.layout.activity_page_setup);
        isEditTextNickNameNull = true;
        isEditTextPinNull = true;
        isAvatarPicked = false;

        color0 = ContextCompat.getColor(this, R.color.setup_color_0);
        color1 = ContextCompat.getColor(this, R.color.setup_color_1);
        color2 = ContextCompat.getColor(this, R.color.setup_color_2);
        color3 = ContextCompat.getColor(this, R.color.setup_color_3);
        color4 = ContextCompat.getColor(this, R.color.setup_color_4);
        color5 = ContextCompat.getColor(this, R.color.setup_color_5);
        colorList = new int[]{color0, color1, color2, color3, color4, color5};

        mPreferencesSetup = new PreferencesSetup(this);

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three = (ImageView) findViewById(R.id.intro_indicator_3);
        four = (ImageView) findViewById(R.id.intro_indicator_4);
        five = (ImageView) findViewById(R.id.intro_indicator_5);
        indicators = new ImageView[]{zero, one, two, three, four, five};

        mViewPager = (ViewPager) findViewById(R.id.container);

        mSectionsPagerAdapter = new AdapterViewPager(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(0));
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(1));
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(2));
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(3));
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(4));
        mSectionsPagerAdapter.addFragment(PageSetupFragment.newInstance(5));
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        mViewPager.addOnPageChangeListener(this);
        mNextBtn.setOnClickListener(this);
        mSkipBtn.setOnClickListener(this);
        mFinishBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_btn_next:
                if (page == avatarPage && !avatarPageIsFinished()) {

                } else {
                    page += 1;
                    mViewPager.setCurrentItem(page, true);
                }
                break;

            case R.id.intro_btn_skip:
                page = adminConfigPage;
                mViewPager.setCurrentItem(page, true);
                break;

            case R.id.intro_btn_finish:
                if (!isEditTextPinNull) {
                    //encriptar pin
                    new SecurityAsyncTask(this, Util.APP_TAG, false).execute(chosenPin);

                    //guardar que setup foi concluido
                    PreferencesSetup userPreference = new PreferencesSetup(getApplicationContext());//guardar nos preferences que o setup foi concluido
                    userPreference.saveFlagSetupPreference(true);

                    finish();
                    startActivity(new Intent(PageSetupActivity.this, ActivityMain.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.setup_toast_warning_pin), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == adminConfigPage ? position : position + 1]);
        mViewPager.setBackgroundColor(colorUpdate);
    }

    @Override
    public void onPageSelected(int position) {
        page = position;
        updateIndicators(page);
        switch (position) {
            case 0:
                mViewPager.setBackgroundColor(color0);
                break;
            case 1:
                mViewPager.setBackgroundColor(color1);
                break;
            case 2:
                mViewPager.setBackgroundColor(color2);
                break;
            case 3:
                mViewPager.setBackgroundColor(color3);
                break;
            case 4:
                mViewPager.setBackgroundColor(color4);
                break;
            case 5:
                mViewPager.setBackgroundColor(color5);
                break;
        }


        if (page == adminConfigPage && avatarPageIsFinished()) {
            //ultima pagina setup
            //pin e avatar concluido
            //criar player na api
            callCreatePlayer();
        }

        if (page == adminConfigPage && !avatarPageIsFinished()) {
            //avatar page nao concluida
            mViewPager.setCurrentItem(avatarPage, true);

        } else {
            mNextBtn.setVisibility(position == adminConfigPage ? View.GONE : View.VISIBLE);
            mFinishBtn.setVisibility(position == adminConfigPage ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onAvatarRecyclerItemSelected(int avatar) {
        isAvatarPicked = true;
        pickedAvatar = avatar;
        mPreferencesSetup.saveFlagAvatarPreference(avatar);
        Toast.makeText(this, getResources().getString(R.string.setup_toast_avatar_sucess), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditTextNickNameUpdate(String input) {
        if (input.equals("")) {
            isEditTextNickNameNull = true;
        } else {
            isEditTextNickNameNull = false;
        }
        pickedNickName = input;
    }

    @Override
    public void onEditTextPinUpdate(String input) {
        if (input.equals("")) {
            isEditTextPinNull = true;
        } else {
            isEditTextPinNull = false;
        }
        chosenPin = input;
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(i == position ? R.drawable.shp_indicator_selected : R.drawable.shp_indicator_unselected);
        }
    }

    private boolean avatarPageIsFinished() {
        if (isEditTextNickNameNull || !isAvatarPicked) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.setup_toast_warning), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void callCreatePlayer() {
        new Request(RequestTypeEnum.POST, this, JsonBuilder.BUILD_PLAYER(pickedNickName, pickedAvatar + "")) {
            @Override
            public void onPostExecute(JSONObject data) {
                super.onPostExecute(data);
            }
        }.execute(Util.SERVER_CREATE_PLAYER);
    }

}
