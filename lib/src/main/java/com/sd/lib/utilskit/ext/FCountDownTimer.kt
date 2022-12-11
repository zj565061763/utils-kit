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

    /** 剩余时长 */
    private var _leftDuration = 0L

    /** 开始的时间 */
    private var _startTime: Long? = null

    /** 暂停时的剩余时长 */
    private var _pauseDuration: Long? = null

    /**
     * 倒计时是否已经启动
     */
    @Synchronized
    fun isStarted(): Boolean = _isStarted

    /**
     * 倒计时是否被暂停
     */
    @Synchronized
    fun isPaused(): Boolean = _pauseDuration != null

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
        val duration = millis.coerceAtLeast(0)
        _leftDuration = duration
        _startTime = SystemClock.elapsedRealtime()
        _isStarted = true
        startTimer(duration)
    }

    /**
     * 暂停
     */
    @Synchronized
    fun pause() {
        if (_isStarted && _pauseDuration == null) {
            val left = _leftDuration - (SystemClock.elapsedRealtime() - _startTime!!)
            if (left > 0) {
                _leftDuration = left
                _pauseDuration = left
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
            _pauseDuration?.let {
                check(it > 0)
                _pauseDuration = null
                _startTime = SystemClock.elapsedRealtime()
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
        _startTime = null
        _pauseDuration = null
        _isStarted = false
    }

    private fun startTimer(duration: Long) {
        createTimer(duration) {
            synchronized(this@FCountDownTimer) {
                if (_isStarted && !isPaused()) {
                    _timer = it
                    it.start()
                }
            }
        }
    }

    private fun createTimer(duration: Long, block: (CountDownTimer) -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            createTimerUiThread(duration, block)
        } else {
            Handler(Looper.getMainLooper()).post { createTimerUiThread(duration, block) }
        }
    }

    private fun createTimerUiThread(duration: Long, block: (CountDownTimer) -> Unit) {
        check(Looper.myLooper() == Looper.getMainLooper())
        object : CountDownTimer(duration, _interval) {
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