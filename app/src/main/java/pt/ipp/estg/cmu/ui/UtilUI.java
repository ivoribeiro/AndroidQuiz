package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by navega on 11/20/16.
 */

public class UtilUI {


    protected static Button newButton(Context context, char c) {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 150);
        params.weight=1;
        button.setLayoutParams(params);
        button.setText(String.valueOf(c));
        return button;
    }

    protected static View newView(Context context) {
        View view = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 150);
        view.setLayoutParams(params);
        return view;
    }

}
