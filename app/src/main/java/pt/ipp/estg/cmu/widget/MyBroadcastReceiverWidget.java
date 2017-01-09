package pt.ipp.estg.cmu.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.setup.PreferencesSetup;
import pt.ipp.estg.cmu.ui.ActivityMain;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.cmu.services.RandQuestionService;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;

import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyBroadcastReceiverWidget extends AppWidgetProvider {

    private Pergunta pergunta;
    private String lastAction = "no_action";
    private static final String RESPOSTACLICK1 = "resposta_click1";
    private static final String RESPOSTACLICK2 = "resposta_click2";
    private static final String RESPOSTACLICK3 = "resposta_click3";
    private static final String RESPOSTACLICK4 = "resposta_click4";
    private static final String RESPOSTACLICK5 = "resposta_click5";
    private static final String RESPOSTACLICK6 = "resposta_click6";
    private static final String RESPOSTACLICK7 = "resposta_click7";
    private static final String RESPOSTACLICK8 = "resposta_click8";
    private static final String RESPOSTACLICK9 = "resposta_click9";
    private static final String RESPOSTACLICK10 = "resposta_click10";
    private static final String RESPOSTACLICK11 = "resposta_click11";
    private static final String RESPOSTACLICK12 = "resposta_click12";
    private static final String RESPOSTACLICK13 = "resposta_click13";
    private static final String RESPOSTACLICK14 = "resposta_click14";
    private static final String RESPOSTACLICK15 = "resposta_click15";

    private static final String RESPOSTACLICKREFRESH = "resposta_click_refresh";

    private static final String LETRAINTENT1 = "letra_intent1";
    private static final String LETRAINTENT2 = "letra_intent2";
    private static final String LETRAINTENT3 = "letra_intent3";
    private static final String LETRAINTENT4 = "letra_intent4";
    private static final String LETRAINTENT5 = "letra_intent5";
    private static final String LETRAINTENT6 = "letra_intent6";
    private static final String LETRAINTENT7 = "letra_intent7";
    private static final String LETRAINTENT8 = "letra_intent8";
    private static final String LETRAINTENT9 = "letra_intent9";
    private static final String LETRAINTENT10 = "letra_intent10";
    private static final String LETRAINTENT11 = "letra_intent11";
    private static final String LETRAINTENT12 = "letra_intent12";
    private static final String LETRAINTENT13 = "letra_intent13";
    private static final String LETRAINTENT14 = "letra_intent14";
    private static final String LETRAINTENT15 = "letra_intent15";

    private static final String REFRESHINTENT = "refresh_intent";


    private static final String PERGUNTAINTENT = "pergunta_intent";
    private static final String INDEXINTENT = "INDEXINTENT";
    private static final String RESPOSTACERTAINTENT = "resposta_certa_intent";
    private static final String STRINGSINTENT = "strings_intent";

    private String resposta = " ";
    private Context mcontext;
    private RemoteViews views;
    private PreferencesSetup mPreferencesSetup;

    protected PendingIntent getPendingSelfIntent(Context context, String action, String letra, Pergunta pergunta, String intent_string) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        Bundle b = new Bundle();
        b.putString("resposta_certa_pergunta", pergunta.getRespostaCerta());
        b.putString("resposta_certa_pergunta", pergunta.getRespostaCerta());
        b.putString("string_aleatoria_pergunta", pergunta.getStringAleatoria());
        b.putString("imagem_pergunta", pergunta.getImagem());
        b.putString(intent_string, letra);
        intent.putExtras(b);

        //RemoteViews remoteV = new RemoteViews(context.getPackageName(), R.layout.widgetmenu);
        //Intent intentSync = new Intent(context, MyBroadcastReceiverWidget.class);
        //intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE); //You need to specify the action for the intent. Right now that intent is doing nothing for there is no action to be broadcasted.
        //PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT); //You need to specify a proper flag for the intent. Or else the intent will become deleted.
        //appWidgetManager.updateAppWidget(awID, remoteV);
        // return pendingSync;
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PerguntaRepo perguntaRepo = new PerguntaRepo(context);
        ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        this.views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        mcontext = context;
        Bundle b = intent.getExtras();
        mPreferencesSetup = new PreferencesSetup(context);

        if (RESPOSTACERTAINTENT.equals(intent.getAction())) {
            pergunta = perguntaRepo.getRandQuestion4letters();
        }

        if (ActivityMain.WIDGET_ACTION.equals(intent.getAction())) {
            this.pergunta = new Pergunta();
            this.pergunta.setImagem(b.getString("imagem_pergunta"));
            this.pergunta.setStringAleatoria(b.getString("string_aleatoria_pergunta"));
            this.pergunta.setRespostaCerta(b.getString("resposta_certa_pergunta"));
            lastAction = intent.getAction();
        }

//Se carregou numa letra para dar a resposta
        else if (RESPOSTACLICK1.equals(intent.getAction()) || RESPOSTACLICK2.equals(intent.getAction()) || RESPOSTACLICK3.equals(intent.getAction()) || RESPOSTACLICK4.equals(intent.getAction()) || RESPOSTACLICK5.equals(intent.getAction()) || RESPOSTACLICK6.equals(intent.getAction()) || RESPOSTACLICK7.equals(intent.getAction()) || RESPOSTACLICK8.equals(intent.getAction()) || RESPOSTACLICK9.equals(intent.getAction()) || RESPOSTACLICK10.equals(intent.getAction()) || RESPOSTACLICK11.equals(intent.getAction()) || RESPOSTACLICK12.equals(intent.getAction()) || RESPOSTACLICK13.equals(intent.getAction()) || RESPOSTACLICK14.equals(intent.getAction()) || RESPOSTACLICK15.equals(intent.getAction())) {
            String letra = "";
            if (intent.hasExtra(LETRAINTENT1)) letra = b.getString(LETRAINTENT1);
            else if (intent.hasExtra(LETRAINTENT2)) letra = b.getString(LETRAINTENT2);
            else if (intent.hasExtra(LETRAINTENT3)) letra = b.getString(LETRAINTENT3);
            else if (intent.hasExtra(LETRAINTENT4)) letra = b.getString(LETRAINTENT4);
            else if (intent.hasExtra(LETRAINTENT5)) letra = b.getString(LETRAINTENT5);
            else if (intent.hasExtra(LETRAINTENT6)) letra = b.getString(LETRAINTENT6);
            else if (intent.hasExtra(LETRAINTENT7)) letra = b.getString(LETRAINTENT7);
            else if (intent.hasExtra(LETRAINTENT8)) letra = b.getString(LETRAINTENT8);
            else if (intent.hasExtra(LETRAINTENT9)) letra = b.getString(LETRAINTENT9);
            else if (intent.hasExtra(LETRAINTENT10)) letra = b.getString(LETRAINTENT10);
            else if (intent.hasExtra(LETRAINTENT11)) letra = b.getString(LETRAINTENT11);
            else if (intent.hasExtra(LETRAINTENT12)) letra = b.getString(LETRAINTENT12);
            else if (intent.hasExtra(LETRAINTENT13)) letra = b.getString(LETRAINTENT13);
            else if (intent.hasExtra(LETRAINTENT14)) letra = b.getString(LETRAINTENT14);
            else if (intent.hasExtra(LETRAINTENT15)) letra = b.getString(LETRAINTENT15);

            this.pergunta = new Pergunta();
            this.pergunta.setImagem(b.getString("imagem_pergunta"));
            this.pergunta.setStringAleatoria(b.getString("string_aleatoria_pergunta"));
            this.pergunta.setRespostaCerta(b.getString("resposta_certa_pergunta"));
            String respostaCerta = b.getString("resposta_certa_pergunta");

            int index = mPreferencesSetup.getIndexRespostaWidget();
            String resposta = mPreferencesSetup.getRespostaActualWidget();
            resposta = resposta + letra;
            mPreferencesSetup.saveRespostaActualWidget(resposta);
            switch (index) {
                case 0:
                    views.setTextViewText(R.id.bt_aw_0, letra);
                    break;
                case 1:
                    views.setTextViewText(R.id.bt_aw_1, letra);
                    break;
                case 2:
                    views.setTextViewText(R.id.bt_aw_2, letra);
                    break;
                case 3:
                    views.setTextViewText(R.id.bt_aw_3, letra);
                    break;
            }
            mPreferencesSetup.saveIndexRespostaWidget(++index);
            if (index == 4) {
                if (respostaCerta.equals(resposta)) {
                    this.limparResposta(mPreferencesSetup);
                    Toast.makeText(context, "Acertou", Toast.LENGTH_SHORT).show();
                    this.pergunta = perguntaRepo.getRandQuestion4letters();
                } else {
                    this.limparResposta(mPreferencesSetup);
                    Toast.makeText(context, "Errou", Toast.LENGTH_SHORT).show();

                }
            }
        } else {
            if (perguntaRepo.getPerguntas4letras().size() > 0) {
                this.pergunta = perguntaRepo.getRandQuestion4letters();
            }
        }
        if (perguntaRepo.getPerguntas4letras().size() > 0) {
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));
            super.onReceive(context, intent);
        }


    }

    public void limparResposta(PreferencesSetup mPreferencesSetup) {
        //limpar botoes
        views.setTextViewText(R.id.bt_aw_0, "");
        views.setTextViewText(R.id.bt_aw_1, "");
        views.setTextViewText(R.id.bt_aw_2, "");
        views.setTextViewText(R.id.bt_aw_3, "");
        //limpar resposta actual
        mPreferencesSetup.saveRespostaActualWidget("");
        //limpar index
        mPreferencesSetup.saveIndexRespostaWidget(0);

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
        Bitmap bitmap = BitmapFactory.decodeFile(pergunta.getImagem());
        views.setBitmap(R.id.question_image_widget, "setImageBitmap", bitmap);

        String letra0 = Character.toString(pergunta.getStringAleatoria().charAt(0));
        String letra1 = Character.toString(pergunta.getStringAleatoria().charAt(1));
        String letra2 = Character.toString(pergunta.getStringAleatoria().charAt(2));
        String letra3 = Character.toString(pergunta.getStringAleatoria().charAt(3));
        String letra4 = Character.toString(pergunta.getStringAleatoria().charAt(4));
        String letra5 = Character.toString(pergunta.getStringAleatoria().charAt(5));
        String letra6 = Character.toString(pergunta.getStringAleatoria().charAt(6));
        String letra7 = Character.toString(pergunta.getStringAleatoria().charAt(7));
        String letra8 = Character.toString(pergunta.getStringAleatoria().charAt(8));
        String letra9 = Character.toString(pergunta.getStringAleatoria().charAt(9));
        String letra10 = Character.toString(pergunta.getStringAleatoria().charAt(10));
        String letra11 = Character.toString(pergunta.getStringAleatoria().charAt(11));
        String letra12 = Character.toString(pergunta.getStringAleatoria().charAt(12));
        String letra13 = Character.toString(pergunta.getStringAleatoria().charAt(13));
        String letra14 = Character.toString(pergunta.getStringAleatoria().charAt(14));

        views.setTextViewText(R.id.bt_0, letra0);
        views.setTextViewText(R.id.bt_1, letra1);
        views.setTextViewText(R.id.bt_2, letra2);
        views.setTextViewText(R.id.bt_3, letra3);
        views.setTextViewText(R.id.bt_4, letra4);
        views.setTextViewText(R.id.bt_6, letra5);
        views.setTextViewText(R.id.bt_7, letra6);
        views.setTextViewText(R.id.bt_8, letra7);
        views.setTextViewText(R.id.bt_9, letra8);
        views.setTextViewText(R.id.bt_10, letra9);
        views.setTextViewText(R.id.bt_11, letra10);
        views.setTextViewText(R.id.bt_12, letra11);
        views.setTextViewText(R.id.bt_13, letra12);
        views.setTextViewText(R.id.bt_14, letra13);
        views.setTextViewText(R.id.bt_15, letra14);

        views.setOnClickPendingIntent(R.id.bt_0, getPendingSelfIntent(mcontext, RESPOSTACLICK1, letra0, pergunta, LETRAINTENT1));
        views.setOnClickPendingIntent(R.id.bt_1, getPendingSelfIntent(mcontext, RESPOSTACLICK2, letra1, pergunta, LETRAINTENT2));
        views.setOnClickPendingIntent(R.id.bt_2, getPendingSelfIntent(mcontext, RESPOSTACLICK3, letra2, pergunta, LETRAINTENT3));
        views.setOnClickPendingIntent(R.id.bt_3, getPendingSelfIntent(mcontext, RESPOSTACLICK4, letra3, pergunta, LETRAINTENT4));
        views.setOnClickPendingIntent(R.id.bt_4, getPendingSelfIntent(mcontext, RESPOSTACLICK5, letra4, pergunta, LETRAINTENT5));
        views.setOnClickPendingIntent(R.id.bt_6, getPendingSelfIntent(mcontext, RESPOSTACLICK6, letra5, pergunta, LETRAINTENT6));
        views.setOnClickPendingIntent(R.id.bt_7, getPendingSelfIntent(mcontext, RESPOSTACLICK7, letra6, pergunta, LETRAINTENT7));
        views.setOnClickPendingIntent(R.id.bt_8, getPendingSelfIntent(mcontext, RESPOSTACLICK8, letra7, pergunta, LETRAINTENT8));
        views.setOnClickPendingIntent(R.id.bt_9, getPendingSelfIntent(mcontext, RESPOSTACLICK9, letra8, pergunta, LETRAINTENT9));
        views.setOnClickPendingIntent(R.id.bt_10, getPendingSelfIntent(mcontext, RESPOSTACLICK10, letra9, pergunta, LETRAINTENT10));
        views.setOnClickPendingIntent(R.id.bt_11, getPendingSelfIntent(mcontext, RESPOSTACLICK11, letra10, pergunta, LETRAINTENT11));
        views.setOnClickPendingIntent(R.id.bt_12, getPendingSelfIntent(mcontext, RESPOSTACLICK12, letra11, pergunta, LETRAINTENT12));
        views.setOnClickPendingIntent(R.id.bt_13, getPendingSelfIntent(mcontext, RESPOSTACLICK13, letra12, pergunta, LETRAINTENT13));
        views.setOnClickPendingIntent(R.id.bt_14, getPendingSelfIntent(mcontext, RESPOSTACLICK14, letra13, pergunta, LETRAINTENT14));
        views.setOnClickPendingIntent(R.id.bt_15, getPendingSelfIntent(mcontext, RESPOSTACLICK15, letra14, pergunta, LETRAINTENT15));

        views.setOnClickPendingIntent(R.id.bt_refresh, getPendingSelfIntent(mcontext, RESPOSTACLICKREFRESH, "", pergunta, REFRESHINTENT));


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
