package com.sd.lib.utilskit

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.sd.lib.ctx.fContext

@JvmOverloads
fun fToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (text is String && text.isEmpty()) return
    if (Looper.myLooper() === Looper.getMainLooper()) {
        Toast.makeText(fContext, text, duration).show()
    } else {
        Handler(Looper.getMainLooper()).post { fToast(text, duration) }
    }
}