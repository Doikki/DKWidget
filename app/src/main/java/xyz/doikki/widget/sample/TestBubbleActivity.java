package xyz.doikki.widget.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import xyz.doikki.widget.dragmsg.MessageBubbleView;

public class TestBubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bubble);

        MessageBubbleView messageBubbleView = findViewById(R.id.msg_view);
        messageBubbleView.setUnreadMsgNum(8);
    }
}
