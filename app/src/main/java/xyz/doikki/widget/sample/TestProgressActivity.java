package xyz.doikki.widget.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import xyz.doikki.widget.progress.CircleProgressBar;

public class TestProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress);

        final CircleProgressBar r1 = findViewById(R.id.rpb_f);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if (i <= 100) {
                    r1.setProgress(i);
                    handler.postDelayed(this, 100);
                    i++;
                }
            }
        });

        final CircleProgressBar r2 = findViewById(R.id.rpb_s);

        final Handler h = new Handler();
        h.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if (i <= 100) {
                    r2.setProgress(i);
                    h.postDelayed(this, 50);
                    i++;
                }
            }
        });
    }
}