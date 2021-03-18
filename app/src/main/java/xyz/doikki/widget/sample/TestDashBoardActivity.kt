package xyz.doikki.widget.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.doikki.widget.dashboard.DashBoard

/**
 * Created by qiuyuan on 2020/7/1
 */
class TestDashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DashBoard(this))
    }
}