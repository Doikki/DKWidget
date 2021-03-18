package xyz.doikki.widget.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SeekBar;

import xyz.doikki.widget.qqstep.StepView;

public class TestStepActivity extends AppCompatActivity {

    private StepView mStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step);

        mStepView = findViewById(R.id.step_view);

        SeekBar seekBar = findViewById(R.id.seek);
        seekBar.setMax(10000);

        mStepView.setMaxStep(10000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStepView.setStep(progress);
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
