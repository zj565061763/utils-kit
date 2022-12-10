package com.sd.lib.utilskit.ext

import android.os.CountDownTimer

/**
 * 倒计时类
 */
abstract class FCountDownTimer {
    private var _timer: CountDownTimer? = null
    private var _interval = 1000L

    /**
     * 倒计时是否已经启动
     */
    @get:Synchronized
    val isStarted: Boolean
        get() = _timer != null

    /**
     * 设置倒计时间隔，默认1000毫秒
     */
    @Synchronized
    fun setInterval(interval: Long) {
        require(interval >= 0)
        _interval = interval
    }

    /**
     * 开始倒计时
     *
     * @param millis 总时长（毫秒）
     */
    @Synchronized
    fun start(millis: Long) {
        stop()
        object : CountDownTimer(millis, _interval) {
            override fun onTick(millisUntilFinished: Long) {
                this@FCountDownTimer.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                stop()
                this@FCountDownTimer.onFinish()
            }
        }.also {
            _timer = it
            it.start()
        }
    }

    /**
     * 停止倒计时
     */
    @Synchronized
    fun stop() {
        _timer?.let {
            it.cancel()
            _timer = null
        }
    }

    protected abstract fun onTick(leftTime: Long)
    protected abstract fun onFinish()
}