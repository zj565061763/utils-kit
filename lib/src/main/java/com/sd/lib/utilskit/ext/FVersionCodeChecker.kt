package com.sd.lib.utilskit.ext

import android.content.Context
import android.content.SharedPreferences
import com.sd.lib.ctx.fContext
import com.sd.lib.utilskit.fVersionCode

class FVersionCodeChecker {

    interface Result {
        /** 版本类型  */
        val type: String

        /** 旧版本  */
        val oldVersion: Long

        /** 当前版本  */
        val currentVersion: Long

        /** 版本是否升级了 */
        val isUpgraded: Boolean get() = currentVersion > oldVersion

        /** 保存当前版本信息，只有[isUpgraded]为true才会保存 */
        fun commit()
    }

    /**
     * 检查版本
     *
     * @param type 要检查的版本类型，默认值为包名
     */
    @JvmOverloads
    @Synchronized
    fun check(type: String = fContext.packageName): Result {
        return CheckResult(
            type = type,
            oldVersion = getCacheVersion(type),
            currentVersion = getVersionCode(),
        )
    }

    @Synchronized
    private fun commit(result: CheckResult) {
        if (result.isUpgraded) {
            saveCacheVersion(result.type, result.currentVersion)
        }
    }

    private inner class CheckResult(
        override val type: String,
        override val oldVersion: Long,
        override val currentVersion: Long
    ) : Result {
        override fun commit() {
            this@FVersionCodeChecker.commit(this)
        }
    }

    companion object {
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

        private fun getVersionCode(): Long = fVersionCode()
    }
}