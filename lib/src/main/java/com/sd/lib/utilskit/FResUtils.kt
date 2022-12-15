package com.sd.lib.utilskit

import com.sd.lib.ctx.fContext

/**
 * 根据文件名获取图片资源id
 */
fun fResIdOfDrawable(name: String): Int? {
    val resId = try {
        val resources = fContext.resources
        resources.getIdentifier(name, "drawable", fContext.packageName)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
    return if (resId == 0) null else resId
}