package pt.ipp.estg.cmu.setup;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterPageSetupAvatar;
import pt.ipp.estg.cmu.callbacks.AdapterPageSetupCallback;
import pt.ipp.estg.cmu.callbacks.FingerprintControllerCallback;
import pt.ipp.estg.cmu.callbacks.TextWatcherHelper;
import pt.ipp.estg.cmu.security.FingerprintController;
import pt.ipp.estg.cmu.ui.ActivityMain;


public class PageSetupActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ImageButton mNextBtn;
    private Button mSkipBtn, mFinishBtn;

    private ImageView zero, one, two, three, four;
    private ImageView[] indicators;
    private int lastLeftValue = 0, page = 0;//  to track page position
    private CoordinatorLayout mCoordinator;

    //admin layout
    private static EditText mEditTextAvatar;
    private static EditText mEditTextPin;
    private static boolean avatarChosen;

    private PreferencesSetup mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        }
        setContentView(R.layout.activity_page_setup);
        avatarChosen = false;
        mPreferences = new PreferencesSetup(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three = (ImageView) findViewById(R.id.intro_indicator_3);
        four = (ImageView) findViewById(R.id.intro_indicator_4);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);
        indicators = new ImageView[]{zero, one, two, three, four};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color0 = ContextCompat.getColor(this, R.color.setup_color_0);
        final int color1 = ContextCompat.getColor(this, R.color.setup_color_1);
        final int color2 = ContextCompat.getColor(this, R.color.setup_color_2);
        final int color3 = ContextCompat.getColor(this, R.color.setup_color_3);
        final int color4 = ContextCompat.getColor(this, R.color.setup_color_4);

        final int[] colorList = new int[]{color0, color1, color2, color3, color4};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //color update
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 4 ? position : position + 1]);
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
                }

                if (page == 4 && !avatarPageIsFinished()) {
                    mViewPager.setCurrentItem(3, true);
                } else {
                    mNextBtn.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                    mFinishBtn.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 3 && !avatarPageIsFinished()) {

                } else {
                    page += 1;
                    mViewPager.setCurrentItem(page, true);
                }
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 4;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mEditTextPin.getText().toString().equals("")) {
                    finish();
                    PreferencesSetup userPreference = new PreferencesSetup(getApplicationContext());//guardar nos preferences que o setup foi concluido
                    userPreference.saveFlagSetupPreference(true);

                    startActivity(new Intent(PageSetupActivity.this, ActivityMain.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.setup_toast_warning_pin), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(i == position ? R.drawable.shp_indicator_selected : R.drawable.shp_indicator_unselected);
        }
    }

    private boolean avatarPageIsFinished() {
        if (mEditTextAvatar.getText().toString().equals("") || !avatarChosen) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.setup_toast_warning), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterPageSetupCallback, FingerprintControllerCallback {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int[] bgs = new int[]{R.drawable.ic_setup_page_0,
                R.drawable.ic_setup_page_1,
                R.drawable.ic_setup_page_2};

        private FrameLayout centerLayout;
        private TextView title;
        private TextView description;
        private ImageView imageView;

        private PreferencesSetup mPreferencesSetup;

        //admin layout
        private LinearLayout mLayoutInfo;
        private LinearLayout mLayoutStatus;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPreferencesSetup = new PreferencesSetup(getContext());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_page_setup, container, false);

            centerLayout = (FrameLayout) rootView.findViewById(R.id.page_setup_layout);
            title = (TextView) rootView.findViewById(R.id.section_label);
            description = (TextView) rootView.findViewById(R.id.section_description);
            imageView = (ImageView) rootView.findViewById(R.id.section_img);

            int position = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

            switch (position) {
                case 0:
                    title.setText(getActivity().getResources().getString(R.string.setup_page0_title));
                    description.setText(getActivity().getResources().getString(R.string.setup_page0_subtitle));
                    imageView.setBackgroundResource(bgs[position]);
                    //UtilUI.setViewAnimation(getActivity(), imageView, R.anim.login_button_popup, 0);
                    break;

                case 1:
                    title.setText(getActivity().getResources().getString(R.string.setup_page1_title));
                    description.setText(getActivity().getResources().getString(R.string.setup_page1_subtitle));
                    imageView.setBackgroundResource(bgs[position]);
                    break;

                case 2:
                    title.setText(getActivity().getResources().getString(R.string.setup_page2_title));
                    description.setText(getActivity().getResources().getString(R.string.setup_page2_subtitle));
                    imageView.setBackgroundResource(bgs[position]);
                    break;

                case 3:
                    LinearLayout avatar_layout = (LinearLayout) inflater.inflate(R.layout.fragment_page_setup_avatar, null, false);
                    centerLayout.removeAllViews();
                    centerLayout.addView(avatar_layout);

                    mEditTextAvatar = (EditText) avatar_layout.findViewById(R.id.edit_nickname);
                    mEditTextAvatar.addTextChangedListener(new TextWatcherHelper(getContext(), mEditTextAvatar));

                    RecyclerView recyclerView = (RecyclerView) avatar_layout.findViewById(R.id.recycler_view);
                    AdapterPageSetupAvatar adapter = new AdapterPageSetupAvatar(getContext(), this);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    recyclerView.setAdapter(adapter);

                    break;

                case 4:
                    LinearLayout admin_layout = (LinearLayout) inflater.inflate(R.layout.fragment_page_setup_admin, null, false);
                    centerLayout.removeAllViews();
                    centerLayout.addView(admin_layout);

                    mEditTextPin = (EditText) admin_layout.findViewById(R.id.edit_pin);
                    mEditTextPin.addTextChangedListener(new TextWatcherHelper(getContext(), mEditTextPin));

                    mLayoutInfo = (LinearLayout) admin_layout.findViewById(R.id.fingerprint_layout_info);
                    mLayoutStatus = (LinearLayout) admin_layout.findViewById(R.id.fingerprint_layout_status);

                    ImageView imageView = (ImageView) admin_layout.findViewById(R.id.fingerprint_icon_status);
                    TextView textView = (TextView) admin_layout.findViewById(R.id.fingerprint_text_status);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        new FingerprintController(getContext(), imageView, textView, this);
                    } else {
                        mLayoutInfo.setVisibility(View.GONE);
                        mLayoutStatus.setVisibility(View.GONE);
                    }
                    break;

            }
            return rootView;
        }


        @Override
        public void onRecyclerItemSelected(int avatar) {
            mPreferencesSetup.saveFlagAvatarPreference(avatar);
            avatarChosen = true;
            Toast.makeText(getContext(), getResources().getString(R.string.setup_toast_avatar_sucess), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fingerprintAuthResult(boolean result) {
            mLayoutInfo.setVisibility(View.GONE);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
