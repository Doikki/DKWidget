package xyz.doikki.customviewcollect.dashboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by qiuyuan on 2020/7/1
 */
class TestDashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DashBoard(this))
    }
}