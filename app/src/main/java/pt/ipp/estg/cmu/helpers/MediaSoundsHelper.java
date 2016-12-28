package pt.ipp.estg.cmu.helpers;

import android.content.Context;
import android.media.MediaPlayer;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.enums.SoundEnum;
import pt.ipp.estg.cmu.settings.PreferencesSettings;

/**
 * Created by Navega on 12/28/2016.
 */

public class MediaSoundsHelper {

    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public MediaSoundsHelper(Context context) {
        mContext = context;
    }

    public void play(SoundEnum type) {
        mMediaPlayer = null;
        switch (type) {
            case BUTTON_CLICK:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_game_button);
                break;

            case CLEAR:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_clear);
                break;

            case HELP:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_help);
                break;

            case UNLOCK:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_unlock_level);
                break;

            case HIT:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_unlock_level);
                break;

            case FAILED:
                mMediaPlayer = MediaPlayer.create(mContext, R.raw.sound_fail);
                break;
        }

        if (new PreferencesSettings(mContext).getSoundPreference()) {
            mMediaPlayer.start();
        }
    }


}
