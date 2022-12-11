package com.sd.lib.utilskit.ext

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.SystemClock

/**
 * 倒计时类
 */
abstract class FCountDownTimer {
    private var _isStarted = false
    private var _timer: CountDownTimer? = null

    @Volatile
    private var _interval = 1000L

    private var _leftTime: Long? = null
    private var _stopTime: Long? = null

    /**
     * 倒计时是否已经启动
     */
    @Synchronized
    fun isStarted(): Boolean = _isStarted

    /**
     * 倒计时是否被暂停
     */
    @Synchronized
    fun isPaused(): Boolean = _leftTime != null

    /**
     * 设置倒计时间隔，默认1000毫秒
     */
    fun setInterval(interval: Long) {
        require(interval > 0)
        _interval = interval
    }

    /**
     * 开始倒计时
     *
     * @param millis 总时长（毫秒）
     */
    @Synchronized
    fun start(millis: Long) {
        cancel()
        val time = millis.coerceAtLeast(0)
        _stopTime = SystemClock.elapsedRealtime() + time
        _isStarted = true
        startTimer(time)
    }

    /**
     * 暂停
     */
    @Synchronized
    fun pause() {
        if (_isStarted && _leftTime == null) {
            val leftTime = _stopTime!! - SystemClock.elapsedRealtime()
            if (leftTime > 0) {
                _leftTime = leftTime
                cancelTimer()
            }
        }
    }

    /**
     * 恢复
     */
    @Synchronized
    fun resume() {
        if (_isStarted) {
            _leftTime?.let {
                check(it > 0)
                _leftTime = null
                startTimer(it)
            }
        }
    }

    /**
     * 取消倒计时
     */
    @Synchronized
    fun cancel() {
        cancelTimer()
        _leftTime = null
        _stopTime = null
        _isStarted = false
    }

    private fun startTimer(time: Long) {
        createTimer(time) {
            synchronized(this@FCountDownTimer) {
                if (_isStarted) {
                    _timer = it
                    if (!isPaused()) {
                        it.start()
                    }
                }
            }
        }
    }

    private fun createTimer(time: Long, block: (CountDownTimer) -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            createTimerUiThread(time, block)
        } else {
            Handler(Looper.getMainLooper()).post { createTimerUiThread(time, block) }
        }
    }

    private fun createTimerUiThread(time: Long, block: (CountDownTimer) -> Unit) {
        check(Looper.myLooper() == Looper.getMainLooper())
        object : CountDownTimer(time, _interval) {
            override fun onTick(leftTime: Long) {
                this@FCountDownTimer.onTick(leftTime)
            }

            override fun onFinish() {
                this@FCountDownTimer.cancel()
                this@FCountDownTimer.onFinish()
            }
        }.also {
            block(it)
        }
    }

    private fun cancelTimer() {
        _timer?.let {
            it.cancel()
            _timer = null
        }
    }

    protected abstract fun onTick(leftTime: Long)
    protected abstract fun onFinish()
}