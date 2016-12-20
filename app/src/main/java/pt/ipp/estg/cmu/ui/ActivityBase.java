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
import pt.ipp.estg.cmu.db.DbHandler;
import pt.ipp.estg.cmu.db.repositories.JogadorRepo;
import pt.ipp.estg.cmu.models.Jogador;
import pt.ipp.estg.cmu.util.Util;

public class ActivityBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //drawer
    protected DrawerLayout mDrawer;
    protected NavigationView mNavigationView;
    protected ActionBarDrawerToggle mDrawerToggle;

    //layout
    private Toolbar mMainToolbar;
    private TextView mUserText;
    private ImageView mUserAvatar;
    private LinearLayout mHeaderLayout;

    //data
    protected Jogador mJogador;
    protected JogadorRepo mRepository;

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

        mMainToolbar = (Toolbar) mDrawer.findViewById(R.id.main_toolbar);
        setSupportActionBar(mMainToolbar);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mMainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        //header
        View header = mNavigationView.getHeaderView(0);
        mUserText = (TextView) header.findViewById(R.id.username);
        mUserAvatar = (ImageView) header.findViewById(R.id.avatar);
        mHeaderLayout = (LinearLayout) header.findViewById(R.id.header_layout);

        mRepository = new JogadorRepo(this);
        new DbHandler(this, DbHandler.DATABASE_NAME);
        mJogador = this.mRepository.getById(1);
        mUserText.setText(mJogador.getUsername());

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
                this.startActivity(new Intent(this, ActivityMain.class));
                break;

            case R.id.nav_statistics:
                this.startActivity(new Intent(this, EstatisticasActivity.class));
                break;

            case R.id.nav_admin:
                this.startActivity(new Intent(this, CategoriaActivity.class).putExtra(Util.ARG_ADMIN, true));
                break;

            case R.id.nav_settings:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
