package com.sd.lib.utilskit.ext

import android.content.Context
import android.content.SharedPreferences
import com.sd.lib.ctx.fContext
import com.sd.lib.utilskit.fVersionCode

class FVersionCode internal constructor(
    /** 版本类型  */
    val type: String,

    /** 旧版本  */
    val oldVersion: Long,

    /** 当前版本  */
    val currentVersion: Long,
) {
    /** 版本是否升级了 */
    val isUpgraded: Boolean get() = currentVersion > oldVersion

    /**
     * 升级到当前版本，只有[isUpgraded]为true才会升级
     */
    fun upgrade() {
        if (isUpgraded) {
            saveCacheVersion(type, currentVersion)
        }
    }

    companion object {
        /**
         * 返回某个类型[type]的版本信息
         */
        @JvmStatic
        @JvmOverloads
        fun of(type: String = fContext.packageName): FVersionCode {
            return FVersionCode(
                type = type,
                oldVersion = getCacheVersion(type),
                currentVersion = fVersionCode(),
            )
        }
    }
}

private const val KeyPrefix = "app_version_code#"

private fun getSharedPreferences(): SharedPreferences {
    val filename = "f_version_code_preferences"
    return fContext.getSharedPreferences(filename, Context.MODE_PRIVATE)
}

private fun getCacheVersion(type: String): Long {
    val key = KeyPrefix + type
    return getSharedPreferences().getLong(key, 0)
}

private fun saveCacheVersion(type: String, version: Long) {
    val key = KeyPrefix + type
    getSharedPreferences().edit().putLong(key, version).commit()
}