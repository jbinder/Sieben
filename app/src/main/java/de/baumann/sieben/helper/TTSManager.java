package de.baumann.sieben.helper;


import android.content.Context;
import android.media.*;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class TTSManager {

    private TextToSpeech mTts = null;
    private boolean isLoaded = false;
    private Context context = null;

    public void init(Context context) {
        this.context = context;
        try {
            mTts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void initQueue(String text) {
        if (isLoaded) {
            final android.media.AudioManager am = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            final int maxVolume = am.getStreamMaxVolume(android.media.AudioManager.STREAM_NOTIFICATION);
            final android.media.AudioManager.OnAudioFocusChangeListener afChangeListener = CreateOnAudioFocusChangeListener(maxVolume);
            mTts.stop();
            int result = am.requestAudioFocus(afChangeListener,
                    android.media.AudioManager.STREAM_NOTIFICATION,
                    android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                String utteranceId = "tts-message";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
                    //noinspection deprecation
                    mTts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                }
                mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                    }

                    @Override
                    public void onDone(String s) {
                        am.abandonAudioFocus(afChangeListener);
                    }

                    @Override
                    public void onError(String s) {
                    }
                });
            }
        }
        else
            Log.e("error", "TTS Not Initialized");
    }

    private AudioManager.OnAudioFocusChangeListener CreateOnAudioFocusChangeListener(final int maxVolume) {
        return new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mTts.stop();
                }
            }
        };
    }
}
