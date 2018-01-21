package de.baumann.sieben.helper;


import android.content.Context;
import android.media.*;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class TTSManager {

    private TextToSpeech mTts = null;
    private boolean isLoaded = false;
    private Context context = null;
    private SoundManager soundManager = null;
    private String text = "";

    public void init(Context context) {
        this.context = context;
        try {
            mTts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        soundManager = createSoundManager();
    }

    public void deinit() {
        if (mTts == null) return;
        mTts.stop();
        mTts.shutdown();
    }

    private final TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                mTts.setLanguage(Locale.getDefault());
                isLoaded = true;
            }
        }
    };

// --Commented out by Inspection START (31.03.16 09:38):
//    public void shutDown() {
//        mTts.shutdown();
//    }
// --Commented out by Inspection STOP (31.03.16 09:38)

// --Commented out by Inspection START (28.03.16 22:50):
//    public void addQueue(String text) {
//        if (isLoaded) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//            } else {
//                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        }
//        else
//            Log.e("error", "TTS Not Initialized");
//    }
// --Commented out by Inspection STOP (28.03.16 22:50)

    public void initQueue(final String text) {
        if (isLoaded) {
            this.text = text;
            soundManager.play();
        }
        else
            Log.e("error", "TTS Not Initialized");
    }

    private SoundManager createSoundManager() {
        return new SoundManager(context) {
            @Override
            public void onPlay() {
                String utteranceId = "tts-message" + UUID.randomUUID();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
                    //noinspection deprecation
                    mTts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                }
                mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    public void onStart(String s) {}
                    public void onDone(String s) { setDone(); }
                    public void onError(String s) {}
                });
            }

            @Override
            public void onStop() {
            }

            @Override
            protected AudioManager.OnAudioFocusChangeListener onCreateOnAudioFocusChangeListener(AudioManager audioManager) {
                return CreateOnAudioFocusChangeListener();
            }
        };
    }

    private AudioManager.OnAudioFocusChangeListener CreateOnAudioFocusChangeListener() {
        // currently whistle and tts might be played at the same time by the app, so don't interrupt the tts
        return new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // mTts.stop();
                }
            }
        };
    }
}
