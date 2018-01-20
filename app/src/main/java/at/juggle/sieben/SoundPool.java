package at.juggle.sieben;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import de.baumann.sieben.R;
import de.baumann.sieben.helper.SoundManager;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * Created by dermotte on 28.09.2016.
 */

public class SoundPool {
    @SuppressWarnings("unused")
    public static int sndWhistle = R.raw.whistle_blow_cc0;

    public static void playWhistle(Context context) {
        final MediaPlayer player = MediaPlayer.create(context, R.raw.whistle_blow_cc0);
        final SoundManager sm = new SoundManager(context) {
            @Override
            public void onPlay() {
                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        setDone();
                    }
                });
            }

            @Override
            public void onStop() {}

            @Override
            protected AudioManager.OnAudioFocusChangeListener onCreateOnAudioFocusChangeListener(AudioManager audioManager) {
                return CreateOnAudioFocusChangeListener(player, audioManager);
            }
        };
        sm.play();
    }

    private static AudioManager.OnAudioFocusChangeListener CreateOnAudioFocusChangeListener(final MediaPlayer player, AudioManager audioManager) {
        // currently whistle and tts might be played at the same time by the app, so don't interrupt the whistle
        return new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    player.stop();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                    // player.pause();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // player.pause();
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    /*
                    if (player.getCurrentPosition() < player.getDuration() && player.getCurrentPosition() > 0) {
                        player.start();
                    }
                    */
                }
            }
        };
    }
}
