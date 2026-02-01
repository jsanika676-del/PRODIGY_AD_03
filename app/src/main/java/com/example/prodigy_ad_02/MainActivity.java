package com.example.prodigy_ad_02;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    Button btnStart, btnPause, btnReset;

    Handler handler = new Handler();
    boolean isRunning = false;

    long startTime = 0L;
    long elapsedTime = 0L;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis() - startTime;
            elapsedTime = currentTime;

            int milliseconds = (int) (elapsedTime % 1000);
            int seconds = (int) (elapsedTime / 1000) % 60;
            int minutes = (int) (elapsedTime / (1000 * 60));

            tvTime.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));

            handler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - elapsedTime;
                handler.post(runnable);
                isRunning = true;
            }
        });

        btnPause.setOnClickListener(v -> {
            if (isRunning) {
                handler.removeCallbacks(runnable);
                isRunning = false;
            }
        });

        btnReset.setOnClickListener(v -> {
            handler.removeCallbacks(runnable);
            isRunning = false;
            elapsedTime = 0L;
            tvTime.setText("00:00:000");
        });
    }
}