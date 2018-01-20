package de.baumann.sieben.helper;

import android.content.Context;
import android.media.AudioManager;
import android.preference.PreferenceManager;

public abstract class SoundManager {
    private AudioManager.OnAudioFocusChangeListener focusChangeListener;
    private boolean requestFocus;
    private android.media.AudioManager audioManager;

    protected SoundManager(Context context) {
        audioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.focusChangeListener = onCreateOnAudioFocusChangeListener(audioManager);
        requestFocus = PreferenceManager.getDefaultSharedPreferences(context).getBoolean ("no_audio_overlap", false);
    }

    protected abstract void onPlay();
    protected abstract void onStop();
    protected abstract AudioManager.OnAudioFocusChangeListener onCreateOnAudioFocusChangeListener(AudioManager audioManager);

    public void play() {
        if (!requestFocus) {
            onPlay();
            return;
        }

        onStop(); // ensure to stop still running sounds
        int result = audioManager.requestAudioFocus(focusChangeListener,
                android.media.AudioManager.STREAM_NOTIFICATION,
                android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            onPlay();
        }
    }

    public void setDone() {
        if (!requestFocus) {
            return;
        }
        audioManager.abandonAudioFocus(focusChangeListener);
    }
}
