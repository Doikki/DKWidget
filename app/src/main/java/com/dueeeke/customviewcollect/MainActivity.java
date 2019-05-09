package com.dueeeke.customviewcollect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dueeeke.customviewcollect.alphabetindex.TestAlphabetActivity;
import com.dueeeke.customviewcollect.qqstep.TestStepActivity;
import com.dueeeke.customviewcollect.trackcolor.TestTrackColorActivity;

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
        }
    }
}
