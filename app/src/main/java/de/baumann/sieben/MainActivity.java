package de.baumann.sieben;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import at.juggle.sieben.SoundPool;
import de.baumann.sieben.helper.Activity_intro;
import de.baumann.sieben.helper.CountDownTimer;
import de.baumann.sieben.helper.OnSwipeTouchListener;
import de.baumann.sieben.helper.UserSettingsActivity;
import de.baumann.sieben.pause.Pause;
import de.baumann.sieben.pause.Pause10;
import de.baumann.sieben.pause.Pause11;
import de.baumann.sieben.pause.Pause2;
import de.baumann.sieben.pause.Pause3;
import de.baumann.sieben.pause.Pause4;
import de.baumann.sieben.pause.Pause5;
import de.baumann.sieben.pause.Pause6;
import de.baumann.sieben.pause.Pause7;
import de.baumann.sieben.pause.Pause8;
import de.baumann.sieben.pause.Pause9;
import de.baumann.sieben.workouts.MainActivity1;
import de.baumann.sieben.workouts.MainActivity10;
import de.baumann.sieben.workouts.MainActivity11;
import de.baumann.sieben.workouts.MainActivity12;
import de.baumann.sieben.workouts.MainActivity2;
import de.baumann.sieben.workouts.MainActivity3;
import de.baumann.sieben.workouts.MainActivity4;
import de.baumann.sieben.workouts.MainActivity5;
import de.baumann.sieben.workouts.MainActivity6;
import de.baumann.sieben.workouts.MainActivity7;
import de.baumann.sieben.workouts.MainActivity8;
import de.baumann.sieben.workouts.MainActivity9;


public class MainActivity extends ActivityBase {

    private TextView textView;
    private TextView textView2;
    private ProgressBar progressBar;
    private ImageView imageView;

    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 5000;

    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_exercises, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        boolean show = sharedPref.getBoolean("intro_notShow", true);

        if (show){
            Intent mainIntent = new Intent(MainActivity.this, Activity_intro.class);
            startActivity(mainIntent);
            overridePendingTransition(0,0);
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        assert imageView != null;

        if (sharedPref.getBoolean("act1", false)) {
            imageView.setImageResource(R.drawable.a01);
        } else  if (sharedPref.getBoolean("act2", false)) {
            imageView.setImageResource(R.drawable.a02);
        } else  if (sharedPref.getBoolean("act3", false)) {
            imageView.setImageResource(R.drawable.a03);
        } else  if (sharedPref.getBoolean("act4", false)) {
            imageView.setImageResource(R.drawable.a04);
        } else  if (sharedPref.getBoolean("act5", false)) {
            imageView.setImageResource(R.drawable.a05);
        } else  if (sharedPref.getBoolean("act6", false)) {
            imageView.setImageResource(R.drawable.a06);
        } else  if (sharedPref.getBoolean("act7", false)) {
            imageView.setImageResource(R.drawable.a07);
        } else  if (sharedPref.getBoolean("act8", false)) {
            imageView.setImageResource(R.drawable.a08);
        } else  if (sharedPref.getBoolean("act9", false)) {
            imageView.setImageResource(R.drawable.a09);
        } else  if (sharedPref.getBoolean("act10", false)) {
            imageView.setImageResource(R.drawable.a10);
        } else  if (sharedPref.getBoolean("act11", false)) {
            imageView.setImageResource(R.drawable.a11);
        } else  if (sharedPref.getBoolean("act12", false)) {
            imageView.setImageResource(R.drawable.a12);
        } else {
            imageView.setImageResource(R.drawable.a01);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        assert progressBar != null;
        progressBar.setRotation(180);

        textView = (TextView) this.findViewById(R.id.timer);
        textView2 = (TextView) this.findViewById(R.id.timer2);
        assert textView2 != null;
        textView2.setText(R.string.start);

        imageView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            public void onSwipeTop() {
                start();
            }

            public void onSwipeRight() {
                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.sn_first);
                    ttsManager.initQueue(text);
                }

                Snackbar.make(imageView, R.string.sn_first, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            public void onSwipeLeft() {

                isCanceled = true;

                if (sharedPref.getBoolean ("tts", false)){

                    if (sharedPref.getBoolean ("tts", false)){

                        if (sharedPref.getBoolean("act2", false)) {
                            String text = getResources().getString(R.string.pau);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act3", false)) {
                            String text = getResources().getString(R.string.pau_2);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act4", false)) {
                            String text = getResources().getString(R.string.pau_3);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act5", false)) {
                            String text = getResources().getString(R.string.pau_4);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act6", false)) {
                            String text = getResources().getString(R.string.pau_5);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act7", false)) {
                            String text = getResources().getString(R.string.pau_6);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act8", false)) {
                            String text = getResources().getString(R.string.pau_7);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act9", false)) {
                            String text = getResources().getString(R.string.pau_8);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act10", false)) {
                            String text = getResources().getString(R.string.pau_9);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act11", false)) {
                            String text = getResources().getString(R.string.pau_10);
                            ttsManager.initQueue(text);
                        } else  if (sharedPref.getBoolean("act12", false)) {
                            String text = getResources().getString(R.string.pau_11);
                            ttsManager.initQueue(text);
                        } else {
                            String text = getResources().getString(R.string.sn_last);
                            ttsManager.initQueue(text);
                        }
                    }
                }

                if (sharedPref.getBoolean("act2", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act3", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause2.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act4", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause3.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act5", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause4.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                }else if (sharedPref.getBoolean("act6", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause5.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act7", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause6.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act8", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause7.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act9", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause8.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                }else if (sharedPref.getBoolean("act10", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause9.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act11", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause10.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act12", false)) {
                    Intent intent_in = new Intent(MainActivity.this, Pause11.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else {
                    Snackbar.make(imageView, R.string.sn_last, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            public void onSwipeBottom() {
                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.sn_pause);
                    ttsManager.initQueue(text);
                }

                isPaused = true;
                Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        onNewIntent(getIntent());
    }

    protected void onNewIntent(final Intent intent) {

        String action = intent.getAction();

        if ("endless_workout".equals(action)) {
            start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent_in = new Intent(MainActivity.this, UserSettingsActivity.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            isCanceled = true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        isCanceled = true;
        finishAffinity();
    }

    private void start () {
        isPaused = false;
        isCanceled = false;

        long millisInFuture = timeRemaining;
        long countDownInterval = 100;

        new CountDownTimer(millisInFuture, countDownInterval){
            public void onTick(long millisUntilFinished){
                if(isPaused || isCanceled) {
                    cancel();
                } else {
                    textView.setText(String.valueOf(millisUntilFinished / 1000));
                    textView2.setText("");
                    int progress = (int) (millisUntilFinished/50);
                    progressBar.setProgress(progress);
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish(){

                if (sharedPref.getBoolean ("beep", false)){
//                            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
//                            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                    SoundPool.playWhistle(getApplicationContext());
                }

                if (sharedPref.getBoolean ("tts", false)){

                    if (sharedPref.getBoolean("act1", false)) {
                        String text = getResources().getString(R.string.act);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act2", false)) {
                        String text = getResources().getString(R.string.act_2);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act3", false)) {
                        String text = getResources().getString(R.string.act_3);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act4", false)) {
                        String text = getResources().getString(R.string.act_4);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act5", false)) {
                        String text = getResources().getString(R.string.act_5);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act6", false)) {
                        String text = getResources().getString(R.string.act_6);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act7", false)) {
                        String text = getResources().getString(R.string.act_7);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act8", false)) {
                        String text = getResources().getString(R.string.act_8);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act9", false)) {
                        String text = getResources().getString(R.string.act_9);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act10", false)) {
                        String text = getResources().getString(R.string.act_10);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act11", false)) {
                        String text = getResources().getString(R.string.act_11);
                        ttsManager.initQueue(text);
                    } else  if (sharedPref.getBoolean("act12", false)) {
                        String text = getResources().getString(R.string.act_12);
                        ttsManager.initQueue(text);
                    } else {
                        String text = getResources().getString(R.string.end);
                        ttsManager.initQueue(text);
                    }
                }

                progressBar.setProgress(0);

                if (sharedPref.getBoolean("act1", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity1.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act2", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act3", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act4", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act5", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity5.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                }else if (sharedPref.getBoolean("act6", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity6.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act7", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity7.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act8", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity8.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act9", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity9.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act10", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity10.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act11", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity11.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else if (sharedPref.getBoolean("act12", false)) {
                    Intent intent_in = new Intent(MainActivity.this, MainActivity12.class);
                    startActivity(intent_in);
                    overridePendingTransition(0, 0);
                    finishAffinity();
                } else {
                    textView.setText(R.string.end);
                }
            }
        }.start();

        if (sharedPref.getBoolean ("tts", false)){
            String text = getResources().getString(R.string.start2);
            ttsManager.initQueue(text);
        }

        Snackbar.make(imageView, R.string.start2, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
