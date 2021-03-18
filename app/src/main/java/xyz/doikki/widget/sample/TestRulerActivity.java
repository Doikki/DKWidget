package xyz.doikki.widget.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
