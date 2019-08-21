package com.dueeeke.customviewcollect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dueeeke.customviewcollect.alphabetindex.TestAlphabetActivity;
import com.dueeeke.customviewcollect.behavior.TestBehaviorActivity;
import com.dueeeke.customviewcollect.circlemenu.TestMenuActivity;
import com.dueeeke.customviewcollect.dragmsg.TestBubbleActivity;
import com.dueeeke.customviewcollect.guide.TestGuideAcitivity;
import com.dueeeke.customviewcollect.like.TestLikeActivity;
import com.dueeeke.customviewcollect.loading.TestLoadingActivity;
import com.dueeeke.customviewcollect.progress.TestProgressActivity;
import com.dueeeke.customviewcollect.qqstep.TestStepActivity;
import com.dueeeke.customviewcollect.ruler.TestRulerActivity;
import com.dueeeke.customviewcollect.trackcolor.TestTrackColorActivity;
import com.dueeeke.customviewcollect.wave.TestWaveActivity;

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
            case R.id.behavior:
                startActivity(new Intent(this, TestBehaviorActivity.class));
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
            case R.id.guide:
                startActivity(new Intent(this, TestGuideAcitivity.class));
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
        }
    }
}
