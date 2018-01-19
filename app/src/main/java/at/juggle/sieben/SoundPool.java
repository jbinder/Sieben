package at.juggle.sieben;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import de.baumann.sieben.R;

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
        final AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        final AudioManager.OnAudioFocusChangeListener afChangeListener = CreateOnAudioFocusChangeListener(player, maxVolume);

        int result = am.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_NOTIFICATION,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    am.abandonAudioFocus(afChangeListener);
                }
            });
        }
    }

    private static AudioManager.OnAudioFocusChangeListener CreateOnAudioFocusChangeListener(final MediaPlayer player, final int maxVolume) {
        return new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    player.stop();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                    player.pause();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    player.setVolume(maxVolume / 2, maxVolume / 2);
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    if (player.getCurrentPosition() < player.getDuration() && player.getCurrentPosition() > 0) {
                        player.start();
                    }
                    player.setVolume(maxVolume, maxVolume);
                }
            }
        };
    }
}
