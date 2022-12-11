package com.sd.demo.utils_kit

import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.lib.utilskit.ext.FDelayTask

class SampleDelayTask : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_count_down)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start -> {
                _task.start(10_000)
            }
            R.id.btn_pause -> _task.pause()
            R.id.btn_resume -> _task.resume()
            R.id.btn_cancel -> _task.cancel()
        }
    }

    private val _task = object : FDelayTask() {
        override fun onRun() {
            val isMain = Looper.getMainLooper() == Looper.myLooper()
            logMsg { "onRun $isMain" }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _task.cancel()
    }
}