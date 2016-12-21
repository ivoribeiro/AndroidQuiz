package pt.ipp.estg.cmu.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import pt.ipp.estg.cmu.R;

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


    /**
     * Animacoes para o setup
     * @param context
     * @param v
     * @param animationId
     * @param offset
     */

    public static void setViewAnimation(Context context, View v, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), animationId);
        if (offset > 0)
            animation.setStartOffset(offset);
        v.startAnimation(animation);
    }


}
