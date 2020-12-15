package com.wolfsea.pluginlibrary.pluginhelper
import android.content.pm.PackageInfo

/**
 *@desc  插件Item实体
 *@author liuliheng
 *@time 2020/12/15  16:06
 **/
open class PluginItem() {

    //包信息
    var packageInfo: PackageInfo? = null

    //插件路径
    var pluginPath: String? = null

    //应用程序名
    var applicationName: String? = null

}