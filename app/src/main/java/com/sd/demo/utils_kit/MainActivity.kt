package com.sd.demo.utils_kit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sample_count_down -> startActivity(Intent(this, SampleCountDown::class.java))
            R.id.btn_sample_delay_task -> startActivity(Intent(this, SampleDelayTask::class.java))
        }
    }
}

inline fun logMsg(block: () -> String) {
    Log.i("utils-kit-demo", block())
}