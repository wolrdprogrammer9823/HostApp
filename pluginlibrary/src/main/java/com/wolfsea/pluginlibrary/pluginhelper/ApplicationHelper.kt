package com.wolfsea.pluginlibrary.pluginhelper
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.io.File

/**
 *@desc   应用程序帮助类
 *@author liuliheng
 *@time 2020/12/15  19:04
 **/
object ApplicationHelper {

    /**
     *@desc 获取Application名称
     *@author:liuliheng
     *@time: 2020/12/15 19:15
    **/
    fun loadApplication(context: Context, apkFile: File): String {

        //首先调用parsePackage获取到apk对象对应的Package对象
        val mPackageParser : Any? = RefInvoke.createObject("android.content.pm.PackageParser")
        val p1 = arrayOf<Class<*>?>(File::class.java, Int::class.java)
        val v1 = arrayOf<Any?>(apkFile, PackageManager.GET_ACTIVITIES)
        val packageObj = RefInvoke.invokeInstanceMethod(mPackageParser, "parsePackage", p1, v1)

        //然后获取ApplicationInfo对象
        val applicationInfo: ApplicationInfo = RefInvoke.getFieldObject(packageObj!!, "applicationInfo") as ApplicationInfo

        //最后返回
        return applicationInfo.className
    }
}