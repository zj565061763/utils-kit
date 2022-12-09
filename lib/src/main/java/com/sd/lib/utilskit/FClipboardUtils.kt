package com.sd.lib.utilskit

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import com.sd.lib.ctx.fContext

private val clipboardManager
    get() = fContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

/**
 * 复制字符串
 */
fun fClipboardSetText(text: String, label: String = "") {
    val clip = ClipData.newPlainText(label, text)
    clipboardManager.setPrimaryClip(clip)
}

/**
 * 粘贴字符串
 */
fun fClipboardGetText(): String {
    val manager = clipboardManager
    if (!manager.hasPrimaryClip()) return ""
    if (manager.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN) != true) return ""
    val item = manager.primaryClip?.getItemAt(0) ?: return ""
    return item.text?.toString() ?: ""
}