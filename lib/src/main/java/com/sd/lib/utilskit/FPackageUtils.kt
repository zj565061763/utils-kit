package com.sd.lib.utilskit

import com.sd.lib.ctx.fContext

/**
 * 启动某个App，默认启动自身
 */
fun fStartApp(packageName: String = fContext.packageName) {
    try {
        val manager = fContext.packageManager
        val intent = manager.getLaunchIntentForPackage(packageName)
        fContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}