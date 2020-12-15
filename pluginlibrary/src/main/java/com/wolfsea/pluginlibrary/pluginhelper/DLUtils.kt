package com.wolfsea.pluginlibrary.pluginhelper
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import java.lang.Exception

/**
 *@desc  工具类
 *@author liuliheng
 *@time 2020/12/15  18:29
 **/
object DLUtils {

    /**
     *@desc 获取包信息
     *@author:liuliheng
     *@time: 2020/12/15 16:18
     **/
    fun getPackageInfo(context: Context, apkFilePath: String): PackageInfo? {

        val packageManager = context.packageManager
        var packageInfo : PackageInfo? = null

        try {
            packageInfo =
                packageManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES)
        } catch (e: Exception) {}

        return packageInfo
    }

    /**
     *@desc 获取引用图标
     *@author:liuliheng
     *@time: 2020/12/15 16:20
     **/
    fun getAppIcon(context: Context, apkFilePath: String): Drawable? {

        val packageManager = context.packageManager
        val packageInfo = getPackageInfo(context, apkFilePath) ?: return null
        val applicationInfo = packageInfo.applicationInfo

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {

            applicationInfo.sourceDir = apkFilePath
            applicationInfo.publicSourceDir = apkFilePath
        }

        return packageManager.getApplicationIcon(applicationInfo)
    }

    /**
     *@desc 获取应用名称
     *@author:liuliheng
     *@time: 2020/12/15 16:27
     **/
    fun getAppLabel(context: Context, apkFilePath: String) : String? {

        val packageManager = context.packageManager
        val packageInfo = getPackageInfo(context, apkFilePath) ?: return null
        val applicationInfo = packageInfo.applicationInfo

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {

            applicationInfo.sourceDir = apkFilePath
            applicationInfo.publicSourceDir = apkFilePath
        }

        return packageManager.getApplicationLabel(applicationInfo).toString()
    }
}