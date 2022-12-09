package com.sd.demo.utils_kit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.lib.coroutine.FScope
import com.sd.lib.utilskit.fToast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val _scope = FScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn -> {
                fToast("hello")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _scope.cancel()
    }
}

inline fun logMsg(block: () -> String) {
    Log.i("utils-kit-demo", block())
}