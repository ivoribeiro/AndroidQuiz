package pt.ipp.estg.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import pt.ipp.estg.cmu.R;

public class ActivityMain extends ActivityBase implements View.OnClickListener {

    private Button mBtStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtStart = (Button) findViewById(R.id.bt_start_game);
        mBtStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_game:
                    this.startActivity(new Intent(this,ActivityLevel.class));
                break;
        }
    }
}
