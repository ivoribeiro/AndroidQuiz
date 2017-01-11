package pt.ipp.estg.cmu.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.settings.PreferencesSettings;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.cmu.ui.ActivityMain;


public class RandQuestionService extends Service {
    private int mRandQuestionTime;
    private boolean wantNotifications;
    private boolean wantVibration;
    PreferencesSettings mPreferenceSettings;
    public static final String QUESTION_TO_WIDGET = "pergunta_a_actualizar";
    private Timer timer = new Timer();


    @SuppressWarnings("unused")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //preferencias
         mPreferenceSettings = new PreferencesSettings(this);

        int minutos = mPreferenceSettings.getFrequenciaUpdate();


        mRandQuestionTime = minutos;

        timer.scheduleAtFixedRate(new mainTask(this), 0, mRandQuestionTime * 1000 * 60);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("SERVICE ONCREATE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        System.out.println("DESTROY SERVICE");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private class mainTask extends TimerTask {
        Intent mIntent;
        Context mcontext;

        mainTask(Context context) {
            mcontext = context;
            mIntent = new Intent(ActivityMain.WIDGET_ACTION);
        }

        public void run() {
            Pergunta pergunta = new PerguntaRepo(mcontext).getRandQuestion4letters();
            Bundle b = new Bundle();
            b.putString("resposta_certa_pergunta", pergunta.getRespostaCerta());
            b.putString("string_aleatoria_pergunta", pergunta.getStringAleatoria());
            b.putString("imagem_pergunta", pergunta.getImagem());
            mIntent.putExtras(b);
            boolean notifications = mPreferenceSettings.wantNotifications();
            boolean vibration = mPreferenceSettings.wantVibration();
            if (notifications) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mcontext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_description));
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
                Ringtone ringtone = mPreferenceSettings.wantRingTone();
                ringtone.play();
                if (vibration){
                    Vibrator v = (Vibrator) mcontext.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(500);
                }
            }
            sendBroadcast(mIntent);
        }
    }


}
