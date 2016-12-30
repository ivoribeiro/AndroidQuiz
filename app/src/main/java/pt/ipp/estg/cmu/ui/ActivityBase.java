package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.settings.SettingsActivity;
import pt.ipp.estg.cmu.setup.PageSetupActivity;
import pt.ipp.estg.cmu.setup.PreferencesSetup;
import pt.ipp.estg.cmu.util.Util;

public class ActivityBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //drawer
    protected DrawerLayout mDrawer;
    protected NavigationView mNavigationView;
    protected ActionBarDrawerToggle mDrawerToggle;

    //layout
    private Toolbar mMainToolbar;
    protected Toolbar mToolbar;
    private TextView mUserText;
    private ImageView mUserAvatar;
    private LinearLayout mHeaderLayout;

    //data
    protected PreferencesSetup mPreferencesSetup;
    protected String mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mDrawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) mDrawer.findViewById(R.id.frame_layout);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(mDrawer);

        Toolbar actualtoolbar;
        mMainToolbar = (Toolbar) mDrawer.findViewById(R.id.main_toolbar);
        if (useToolbar()) {
            mMainToolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(mMainToolbar);
            actualtoolbar = mMainToolbar;
        } else {
            mMainToolbar.setVisibility(View.GONE);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            actualtoolbar = mToolbar;
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, actualtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);
        //mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        //header
        View header = mNavigationView.getHeaderView(0);
        mUserText = (TextView) header.findViewById(R.id.username);
        mUserAvatar = (ImageView) header.findViewById(R.id.avatar);
        mHeaderLayout = (LinearLayout) header.findViewById(R.id.header_layout);

        //data preferences
        mPreferencesSetup = new PreferencesSetup(this);
        if (!mPreferencesSetup.getFlagSetupPreference()) {
            startActivity(new Intent(ActivityBase.this, PageSetupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {
            mUserName = mPreferencesSetup.getFlagNickNamePreference();
            setAvatar(mPreferencesSetup.getFlagAvatarPreference());
            mUserText.setText(mUserName);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_level) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_game:
                startActivity(new Intent(this, ActivityMain.class));
                break;

            case R.id.nav_statistics:
                startActivity(new Intent(this, EstatisticasActivity.class));
                break;

            case R.id.nav_admin:
                startActivity(new Intent(this, CategoriaActivity.class).putExtra(Util.ARG_ADMIN, true));
                break;

            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected boolean useToolbar() {
        return true;
    }

    private void setAvatar(int avatar) {
        switch (avatar) {
            case 0:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_0));
                break;
            case 1:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_1));
                break;
            case 2:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_2));
                break;
            case 3:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_3));
                break;
            case 4:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_4));
                break;
            case 5:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_5));
                break;
            case 6:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_6));
                break;
            case 7:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_7));
                break;
            case 8:
                mUserAvatar.setBackground(getDrawable(R.drawable.img_avatar_8));
                break;
        }
    }
}
