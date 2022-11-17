package xyz.doikki.widget.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.doikki.widget.sample.databinding.ActivityTestPieBinding

class TestPieActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityTestPieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}