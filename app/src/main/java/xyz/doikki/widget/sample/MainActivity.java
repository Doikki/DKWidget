package xyz.doikki.widget.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.qq_step:
                startActivity(new Intent(this, TestStepActivity.class));
                break;
            case R.id.track_color:
                startActivity(new Intent(this, TestTrackColorActivity.class));
                break;
            case R.id.alphabet:
                startActivity(new Intent(this, TestAlphabetActivity.class));
                break;
            case R.id.loading:
                startActivity(new Intent(this, TestLoadingActivity.class));
                break;
            case R.id.msg:
                startActivity(new Intent(this, TestBubbleActivity.class));
                break;
            case R.id.like:
                startActivity(new Intent(this, TestLikeActivity.class));
                break;
            case R.id.progress:
                startActivity(new Intent(this, TestProgressActivity.class));
                break;
            case R.id.circle_menu:
                startActivity(new Intent(this, TestMenuActivity.class));
                break;
            case R.id.wave:
                startActivity(new Intent(this, TestWaveActivity.class));
                break;
            case R.id.ruler:
                startActivity(new Intent(this, TestRulerActivity.class));
                break;
            case R.id.dash_board:
                startActivity(new Intent(this, TestDashBoardActivity.class));
                break;
        }
    }
}
