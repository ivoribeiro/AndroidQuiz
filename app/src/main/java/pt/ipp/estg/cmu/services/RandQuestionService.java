package pt.ipp.estg.cmu.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.ui.ActivityMain;


public class RandQuestionService extends Service {
    private int mRandQuestionTime;
    private Pergunta mPergunta;
    public static final String QUESTION_TO_WIDGET = "pergunta_a_actualizar";


    @SuppressWarnings("unused")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mRandQuestionTime = intent.getExtras().getInt(ActivityMain.RAND_QUESTION_TIME);


        Intent widgetIntent = new Intent(ActivityMain.WIDGET_ACTION);
        Pergunta pergunta = new PerguntaRepo(this).getById(1);
        widgetIntent.putExtra(QUESTION_TO_WIDGET, pergunta);
        sendBroadcast(widgetIntent);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
