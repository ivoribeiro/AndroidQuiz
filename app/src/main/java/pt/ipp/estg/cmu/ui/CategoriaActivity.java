package pt.ipp.estg.cmu.ui;

import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.AdminLoginListener;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends ActivityBase implements AdminLoginListener {

    //layout
    private boolean isAdmin;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new PreferencesSettings(this).getThemePreference());
        setContentView(R.layout.activity_categoria);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);
        if (isAdmin) {
            super.mActualToolbar.setSubtitle(getResources().getString(R.string.admin_toolbar_subtitle));
        }

        if (isAdmin) {
            mNavigationView.setCheckedItem(R.id.nav_admin);
            if (null == savedInstanceState) {

                FragmentManager fm = getFragmentManager();
                CategoriaAdminDialog dialogFragment = new CategoriaAdminDialog();
                dialogFragment.show(fm, "Dialog Fragment");
                dialogFragment.setCancelable(false);

                //buildDialog();
            }
        } else {
            mNavigationView.setCheckedItem(R.id.nav_game);
            if (null == savedInstanceState) {
                startFragmetCategoria();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onLoginSucess() {
        startFragmetCategoria();
    }

    private void startFragmetCategoria() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }

}
