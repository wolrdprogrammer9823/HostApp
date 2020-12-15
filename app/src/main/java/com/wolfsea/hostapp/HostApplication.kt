package com.wolfsea.hostapp
import android.app.Application
import android.content.Context
import com.wolfsea.pluginlibrary.pluginhelper.PluginManager

/**
 *@desc  自定义应用程序类
 *@author liuliheng
 *@time 2020/12/14  18:37
 **/
class HostApplication : Application() {

    override fun attachBaseContext(base: Context) {

        super.attachBaseContext(base)

        context = this

        PluginManager.init(this)
    }

    override fun onCreate() {

        super.onCreate()

        //调用插件中的Application中的onCreate()方法
        for (pluginItem in PluginManager.plugins) {

             val clazz : Class<*>? = PluginManager.mNowClassLoader?.loadClass(pluginItem.applicationName)
             val application : Application = clazz?.newInstance() as Application
             application.onCreate()
        }
    }

    companion object {
        var context : Context? = null
        private set
    }
}