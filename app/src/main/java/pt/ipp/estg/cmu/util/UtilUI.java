package pt.ipp.estg.cmu.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pt.ipp.estg.cmu.R;

/**
 * Created by navega on 11/20/16.
 */

public class UtilUI {


    public static Button newButton(Context context, char c) {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 150);
        params.weight = 1;
        button.setLayoutParams(params);
        button.setTextColor(context.getResources().getColor(R.color.primary_dark));
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setText(String.valueOf(c));
        return button;
    }

    public static View newView(Context context) {
        View view = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 150);
        view.setLayoutParams(params);
        return view;
    }

}
