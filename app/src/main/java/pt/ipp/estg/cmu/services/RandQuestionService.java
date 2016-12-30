package pt.ipp.estg.cmu.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.NivelRepo;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.ui.ActivityMain;


public class RandQuestionService extends Service {
    private int mRandQuestionTime;
    private Pergunta mPergunta;
    public static final String QUESTION_TO_WIDGET = "pergunta_a_actualizar";
    private static Timer timer = new Timer();


    @SuppressWarnings("unused")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mRandQuestionTime = intent.getExtras().getInt(ActivityMain.RAND_QUESTION_TIME);
        timer.scheduleAtFixedRate(new mainTask(this), 0, mRandQuestionTime * 1000 * 60);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private class mainTask extends TimerTask {
        Intent mIntent;
        Context mcontext;

        mainTask(Context context) {
            mcontext = context;
            mIntent = new Intent(ActivityMain.WIDGET_ACTION);

        }

        public void run() {
            NivelRepo nivelRepo = new NivelRepo(mcontext);
            Nivel randnivel = nivelRepo.getRandNivel();
            Pergunta pergunta = new PerguntaRepo(mcontext).getRandQuestion(randnivel.getId());
            mIntent.putExtra(QUESTION_TO_WIDGET, pergunta);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(mcontext)
                            .setSmallIcon(R.drawable.ic_fingerprint)
                            .setContentTitle(getString(R.string.notification_title))
                            .setContentText(getString(R.string.notification_description ));
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(0, mBuilder.build());
            sendBroadcast(mIntent);
        }
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
