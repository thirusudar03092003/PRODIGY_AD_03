package com.example.stopwacth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;

    private long startTime = 0;
    private boolean isRunning = false;
    private final Handler handler = new Handler();
    private Runnable runnable;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnReset = findViewById(R.id.btnReset);

        // Start Button
        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                isRunning = true;
                handler.post(runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (isRunning) {
                            long elapsedTime = System.currentTimeMillis() - startTime;
                            tvTimer.setText(formatTime(elapsedTime));
                            handler.postDelayed(this, 10); // Update every 10ms
                        }
                    }
                });
            }
        });

        // Pause Button
        btnPause.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(runnable);
        });

        // Reset Button
        btnReset.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(runnable);
            tvTimer.setText("00:00:000");
            startTime = 0;
        });
    }

    // Format time as MM:SS:MS
    @SuppressLint("DefaultLocale")
    private String formatTime(long elapsedTime) {
        int minutes = (int) (elapsedTime / (1000 * 60));
        int seconds = (int) (elapsedTime / 1000) % 60;
        int milliseconds = (int) (elapsedTime % 1000);
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}
