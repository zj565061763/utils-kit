package com.sd.lib.utilskit.ext

import android.os.CountDownTimer

/**
 * 倒计时类
 */
abstract class FCountDownTimer {
    private var _timer: CountDownTimer? = null
    private var _interval = 1000L
    private var _leftTime: Long? = null

    /**
     * 倒计时是否已经启动
     */
    @get:Synchronized
    val isStarted: Boolean
        get() = _timer != null || _leftTime != null

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
            override fun onTick(leftTime: Long) {
                _leftTime = leftTime
                this@FCountDownTimer.onTick(leftTime)
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
        stopInternal()
    }

    /**
     * 暂停计时
     */
    @Synchronized
    fun pause() {
        if (isStarted) {
            stopInternal(resetLeftTime = false)
        }
    }

    /**
     * 恢复计时
     */
    @Synchronized
    fun resume() {
        _leftTime?.let {
            start(it)
        }
    }

    private fun stopInternal(resetLeftTime: Boolean = true) {
        _timer?.let {
            it.cancel()
            _timer = null
        }
        if (resetLeftTime) {
            _leftTime = null
        }
    }

    protected abstract fun onTick(leftTime: Long)
    protected abstract fun onFinish()
}