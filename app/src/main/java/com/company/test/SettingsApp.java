package com.company.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsApp extends AppCompatActivity implements View.OnClickListener {
    Switch soundSwitch;
    Switch vibroSwitch;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        soundSwitch = findViewById(R.id.switchSound);
        vibroSwitch = findViewById(R.id.switchVibro);
        ImageView textSound = findViewById(R.id.textOnOffSound);
        ImageView textVibro = findViewById(R.id.textOnOffVibro);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_SYSTEM, true);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isSoundEnabled = sharedPreferences.getBoolean("sound_enabled", true);
        isSoundEnabled = true;
        soundSwitch.setChecked(isSoundEnabled);
        if (isSoundEnabled) {
            textSound.setImageResource(R.drawable.on);
        }
        else {
            textSound.setImageResource(R.drawable.off);
        }

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                action();
                if (isChecked) {
                    textSound.setImageResource(R.drawable.on);
                }
                else {
                    textSound.setImageResource(R.drawable.off);
                }
                editor.putBoolean("sound_enabled", isChecked);
                editor.apply();
            }
        });

        boolean isVibroEnabled = sharedPreferences.getBoolean("vibro_enabled", true);
        isVibroEnabled = true;
        vibroSwitch.setChecked(isVibroEnabled);
        if (isVibroEnabled) {
            textVibro.setImageResource(R.drawable.on);
        }
        else {
            textVibro.setImageResource(R.drawable.off);
        }

        vibroSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                action();
                if (isChecked) {
                    textVibro.setImageResource(R.drawable.on);
                }
                else {
                    textVibro.setImageResource(R.drawable.off);
                }
                editor.putBoolean("vibro_enabled", isChecked);
                editor.apply();
            }
        });

    }

    public void action() {
        if (sharedPreferences.getBoolean("vibro_enabled", true)) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
        }

        if (sharedPreferences.getBoolean("sound_enabled", true)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(SettingsApp.this, R.raw.click);
            mediaPlayer.setVolume(0.2f, 0.2f);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        action();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        action();
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}