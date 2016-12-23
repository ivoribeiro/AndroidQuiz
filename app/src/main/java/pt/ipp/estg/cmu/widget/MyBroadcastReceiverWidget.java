package pt.ipp.estg.cmu.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import pt.ipp.estg.cmu.ui.ActivityMain;

public class MyBroadcastReceiverWidget extends AppWidgetProvider {

    private String lastAction = "No action";
    private String lastMsg = "No message";


    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if (intent.hasExtra(ActivityMain.INTENT_MESSAGE_EXTRA))
            lastMsg = intent.getStringExtra(ActivityMain.INTENT_MESSAGE_EXTRA);

        lastAction = intent.getAction();

        // executa onUpdate sempre que recebe um Intent neste caso trata-se de um Intents do tipo
        onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // update a cada insignia da widget
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // cria um objecto RemoteViews com o layout da widget
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        //views.setTextViewText(R.id.txtMsg, lastMsg + " (" + lastAction + ")");
        //appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
