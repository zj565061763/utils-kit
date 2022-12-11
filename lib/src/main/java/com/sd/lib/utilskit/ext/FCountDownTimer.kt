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
    @Synchronized
    fun isStarted(): Boolean = _timer != null || _leftTime != null

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
        cancel()
        object : CountDownTimer(millis, _interval) {
            override fun onTick(leftTime: Long) {
                synchronized(this@FCountDownTimer) {
                    _leftTime = leftTime
                }
                this@FCountDownTimer.onTick(leftTime)
            }

            override fun onFinish() {
                cancel()
                this@FCountDownTimer.onFinish()
            }
        }.also {
            _timer = it
            it.start()
        }
    }

    /**
     * 取消倒计时
     */
    @Synchronized
    fun cancel() {
        cancelInternal()
    }

    /**
     * 暂停
     */
    @Synchronized
    fun pause() {
        if (isStarted()) {
            cancelInternal(resetLeftTime = false)
        }
    }

    /**
     * 恢复
     */
    @Synchronized
    fun resume() {
        _leftTime?.let {
            start(it)
        }
    }

    private fun cancelInternal(resetLeftTime: Boolean = true) {
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