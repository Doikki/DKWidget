package xyz.doikki.widget.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import xyz.doikki.widget.ruler.RulerView;


public class TestRulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ruler);

        RulerView rulerView = findViewById(R.id.ruler);
        final TextView result = findViewById(R.id.result);

        rulerView.setOnScaleChangeListener(new RulerView.OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scale) {
                result.setText(scale + "kg");
            }
        });
    }
}
