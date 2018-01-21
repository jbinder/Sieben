package de.baumann.sieben;

import android.app.Application;

import de.baumann.sieben.helper.TTSManager;

public class SiebenApplication extends Application {

    // The ttsManager is on the application level because TTS is played across activities
    private TTSManager ttsManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        ttsManager = new TTSManager();
        ttsManager.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ttsManager.deinit();
    }

    public TTSManager getTtsManager() {
        return ttsManager;
    }
}
