package xyz.doikki.customviewcollect.like;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xyz.doikki.customviewcollect.R;

public class TestLikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_like);

        final LikeView likeView = findViewById(R.id.like);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeView.addDrawable();
            }
        });
    }
}
