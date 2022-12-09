package com.sd.lib.utilskit

import com.sd.lib.ctx.fContext

private val displayMetrics = fContext.resources.displayMetrics

/**
 * 屏幕宽度
 */
fun fDisplayWidth(): Int {
    return displayMetrics.widthPixels
}

/**
 * 屏幕高度
 */
fun fDisplayHeight(): Int {
    return displayMetrics.heightPixels
}

/**
 * dp -> px
 */
fun fDisplayDpToPx(dp: Float): Int {
    return with(displayMetrics) {
        (dp * density + 0.5f).toInt()
    }
}

/**
 * px -> dp
 */
fun fDisplayPxToDp(px: Float): Int {
    return with(displayMetrics) {
        (px / density + 0.5f).toInt()
    }
}