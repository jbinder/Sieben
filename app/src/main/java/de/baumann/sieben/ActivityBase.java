package de.baumann.sieben;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.baumann.sieben.helper.TTSManager;

public abstract class ActivityBase extends AppCompatActivity {
    protected TTSManager ttsManager = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ttsManager = ((SiebenApplication) this.getApplication()).getTtsManager();
    }
}
