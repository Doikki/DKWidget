package xyz.doikki.customviewcollect.dragmsg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.doikki.customviewcollect.R;

public class TestBubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bubble);

        MessageBubbleView messageBubbleView = findViewById(R.id.msg_view);
        messageBubbleView.setUnreadMsgNum(8);
    }
}
