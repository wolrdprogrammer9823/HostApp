package com.wolfsea.pluginhelper
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 *@desc   插件管理器类
 *@author liuliheng
 *@time 2020/12/15  16:13
 **/
object PluginManager {

    val plugins : MutableList<PluginItem> = ArrayList()

    val pluginsInfo: HashMap<String, PluginInfo> = HashMap()

    //正在使用的Resources
    @Volatile
    var mNowResources : Resources? = null

    //原始Application的Context,不能是其他的,否则会内存泄漏
    @Volatile
    var mBaseContext : Context? = null

    //ContextImpl类中的LoadedAPK对象mPackageInfo
    var mPackageInfo : Any? = null

    //系统原始的ClassLoader
    @Volatile
    var mNowClassLoader : ClassLoader? = null

    //系统原始的ClassLoader
    @Volatile
    var mBaseClassLoader : ClassLoader? = null

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2020/12/15 23:01
    **/
    fun init(application : Application) {

        mPackageInfo = RefInvoke.getFieldObject(application.baseContext, "mPackageInfo")
        mBaseContext = application.baseContext
        mNowResources = mBaseContext?.resources

        mBaseClassLoader = mBaseContext?.classLoader
        mNowClassLoader = mBaseContext?.classLoader

        try {
            val assetManager = application.assets
            val paths: Array<String> = assetManager.list("") as Array<String>

            val pluginPaths = mutableListOf<String>()
            for (path in paths) {

                val mIsTarget = path.endsWith("apk")
                if (mIsTarget) {

                    PluginResourcesExtractUtil.extractAssets(mBaseContext!!, path)
                    val pluginItem = generatePluginItem(path)
                    plugins.add(pluginItem)

                    pluginPaths.add(pluginItem.pluginPath!!)
                }
            }

            reloadInstalledPluginResources(pluginPaths)
        } catch (e: Exception) {}

        val classLoader = ZeusClassLoader(mBaseContext?.packageCodePath, mBaseContext?.classLoader)
        val dexOutputDir = mBaseContext?.getDir("dex", Context.MODE_PRIVATE)
        val dexOutputPath = dexOutputDir?.absolutePath
        for (pluginItem in plugins) {

            val dexClassLoader = DexClassLoader(pluginItem.pluginPath, dexOutputPath,
                    null, mBaseClassLoader!!)
            classLoader.addPluginClassLoader(dexClassLoader)
        }

        RefInvoke.setFieldObject(mPackageInfo!!, "mClassLoader", classLoader)
        Thread.currentThread().contextClassLoader = classLoader
        mNowClassLoader = classLoader
    }


    /**
     *@desc 生成PluginItem
     *@author:liuliheng
     *@time: 2020/12/15 22:22
    **/
    private fun generatePluginItem(apkName : String) : PluginItem {

        val file = mBaseContext?.getFileStreamPath(apkName)

        val pluginItem = PluginItem()
        pluginItem.pluginPath = file?.absolutePath
        pluginItem.packageInfo = DLUtils.getPackageInfo(mBaseContext!!, pluginItem.pluginPath!!)
        pluginItem.applicationName = ApplicationHelper.loadApplication(mBaseContext!!, file!!)

        return pluginItem
    }

    /**
     *@desc 生产PluginInfo
     *@author:liuliheng
     *@time: 2020/12/16 22:23
    **/
    fun generatePluginInfo(context: Context,pluginName: String) {

        val extractFile = context.getFileStreamPath(pluginName)
        val releaseFile = context.getDir("dex", Context.MODE_PRIVATE)

        val dexPath = extractFile.path
        val dexClassLoader = DexClassLoader(dexPath, releaseFile.absolutePath, null, context.classLoader)
        pluginsInfo[pluginName] = PluginInfo(dexPath, dexClassLoader)
    }

    /**
     *@desc 重新加载已安装的插件资源
     *@author:liuliheng
     *@time: 2020/12/15 22:27
     **/
    private fun reloadInstalledPluginResources(pluginPaths: MutableList<String>) {
        try {

            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = AssetManager::class.java.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, mBaseContext?.packageResourcePath)

            for (pluginPath in pluginPaths) {

                addAssetPath.invoke(assetManager, pluginPath)
            }

            val newResources = Resources(assetManager, mBaseContext?.resources?.displayMetrics,
                                                       mBaseContext?.resources?.configuration)

            RefInvoke.setFieldObject(mBaseContext!!, "mResources", newResources)
            //这个是最主要的替换
            RefInvoke.setFieldObject(mPackageInfo!!, "mResources", newResources)

            mNowResources = newResources

            //需要清理mTheme对象,否则通过inflate加载资源会爆粗
            //如果是activity动态加载插件,则需要把activity的mTheme也设置为null
            RefInvoke.setFieldObject(mBaseContext!!, "mTheme", null)
        } catch (throwable: Throwable) {

            throwable.printStackTrace()
        }
    }

}