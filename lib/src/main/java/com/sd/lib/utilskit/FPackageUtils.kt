package com.sd.lib.utilskit

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build
import com.sd.lib.ctx.fContext

private val packageManager = fContext.packageManager

/**
 * 当前App的版本信息
 */
fun fVersionCode(): Long {
    val packageInfo = fPackageInfo()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }
}

/**
 * 当前App的版本信息
 */
fun fVersionName(): String {
    return fPackageInfo().versionName
}

/**
 * 当前App的包信息
 */
fun fPackageInfo(flags: Int = PackageManager.GET_CONFIGURATIONS): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(fContext.packageName, PackageInfoFlags.of(flags.toLong()))
    } else {
        packageManager.getPackageInfo(fContext.packageName, flags)
    }
}

/**
 * 启动某个App，默认启动自身
 */
fun fStartApp(packageName: String = fContext.packageName) {
    try {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        fContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}