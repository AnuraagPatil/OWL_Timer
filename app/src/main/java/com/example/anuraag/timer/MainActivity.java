package com.example.anuraag.timer;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView textView;
    Button button;
    CountDownTimer countDownTimer;

    public void updateTimer(int progress){

        int minutes = progress/60;
        int seconds = progress - minutes*60;
        if(seconds>=10)
            textView.setText(minutes+":"+seconds);
        else
            textView.setText(minutes+":0"+seconds);
    }

    public void controlTimmer(View view){

        if(button.getText().equals("START")) {
            timerSeekbar.setVisibility(View.INVISIBLE);
            button.setText("STOP");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    //Countdown is counting every sec
                    Log.i("OnTick", "Seconds remaining : " + (millisUntilFinished) / 1000);
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    //Counter is Finished
                    Log.i("OnFinish", "Timer Finished");
                    textView.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.buzzer);
                    mplayer.start();
                    timerSeekbar.setVisibility(View.VISIBLE);
                    timerSeekbar.setProgress(0);
                    button.setText("START");
                }
            }.start();
        }else {
            timerSeekbar.setVisibility(View.VISIBLE);
            timerSeekbar.setProgress(30);
            textView.setText("0:30");
            countDownTimer.cancel();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        METHOD 1 : USING HANDLER

        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //Insert code to be run every sec
                Log.i("Runnable","a second has passed...");
                handler.postDelayed(this,1000);
            }
        };
        handler.post(run);
        */

        textView = findViewById(R.id.txt);
        timerSeekbar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
