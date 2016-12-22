package pt.ipp.estg.cmu.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.util.Util;

public class CategoriaActivity extends AppCompatActivity {

    //layout
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isAdmin = getIntent().getBooleanExtra(Util.ARG_ADMIN, false);

        if (isAdmin) {
            buildDialog();
        } else {
            startFragmetCategoria();
        }
    }


    private void buildDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sign_in);
        dialog.setTitle(getResources().getString(R.string.dialog_title));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        final EditText editText = (EditText) dialog.findViewById(R.id.edit_pin);

        Button ok = (Button) dialog.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                System.out.println(editText.getText().toString());
                startFragmetCategoria();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void startFragmetCategoria() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, CategoriaFragment.newInstance(isAdmin))
                .commit();
    }
}
