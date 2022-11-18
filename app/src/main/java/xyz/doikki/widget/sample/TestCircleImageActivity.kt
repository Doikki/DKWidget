package xyz.doikki.widget.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.doikki.widget.sample.databinding.ActivityTestCircleImageBinding

class TestCircleImageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTestCircleImageBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}