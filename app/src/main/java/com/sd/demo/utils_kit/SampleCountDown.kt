package com.sd.demo.utils_kit

import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.lib.coroutine.FScope
import com.sd.lib.utilskit.ext.FCountDownTimer

class SampleCountDown : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_count_down)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start -> {
                _timer.start(10_000)
            }
            R.id.btn_pause -> _timer.pause()
            R.id.btn_resume -> _timer.resume()
            R.id.btn_cancel -> _timer.cancel()
        }
    }

    private val _timer = object : FCountDownTimer() {
        override fun onTick(leftTime: Long) {
            val isMain = Looper.getMainLooper() == Looper.myLooper()
            logMsg { "onTick ${leftTime / 1000} $leftTime $isMain" }
        }

        override fun onFinish() {
            val isMain = Looper.getMainLooper() == Looper.myLooper()
            logMsg { "onFinish $isMain" }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _timer.cancel()
    }
}