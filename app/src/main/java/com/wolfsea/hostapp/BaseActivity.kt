package com.wolfsea.hostapp
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wolfsea.pluginhelper.PluginManager
import com.wolfsea.pluginhelper.PluginResourcesExtractUtil
import dalvik.system.DexClassLoader
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File
import java.lang.reflect.Method

const val SOURCE_NAME0 = "plugin0-debug.apk"
const val SOURCE_NAME1 = "plugin1-debug.apk"

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        try {

            PluginResourcesExtractUtil.extractAssets(newBase, SOURCE_NAME0)
            PluginResourcesExtractUtil.extractAssets(newBase, SOURCE_NAME1)
        } catch (e: Exception) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val extractFile = getFileStreamPath(SOURCE_NAME0)
        dexPath = extractFile.path
        fileRelease = getDir("dex", Context.MODE_PRIVATE)
        mDexClassLoader = DexClassLoader(dexPath, fileRelease?.absolutePath, null, classLoader)

        PluginManager.generatePluginInfo(this, SOURCE_NAME0)
        PluginManager.generatePluginInfo(this, SOURCE_NAME1)
    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁Disposable,防止内存泄漏
        compositeDisposable.dispose()
    }

    override fun getResources(): Resources {
        return if (mResources == null) super.getResources() else mResources!!
    }

    override fun getAssets(): AssetManager {
        return if (mAssetManager == null) super.getAssets() else mAssetManager!!
    }

    override fun getTheme(): Resources.Theme {
        return if (mTheme == null) super.getTheme() else mTheme!!
    }

    /**
     *@desc 加载资源丰富
     *@author:liuliheng
     *@time: 2020/12/16 18:03
    **/
    fun loadResources() {
        try {

            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath : Method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, dexPath!!)
            mAssetManager = assetManager
        } catch (e: Exception) {}

        val superResources = resources
        superResources.displayMetrics
        superResources.configuration

        mResources = Resources(mAssetManager, superResources.displayMetrics, superResources.configuration)
        mTheme = mResources?.newTheme()
        mTheme?.setTo(super.getTheme())
    }

    fun loadResources(mDexPath: String) {
        try {

            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath: Method =
                assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, mDexPath)
            mAssetManager = assetManager
        } catch (e: Exception) {
        }

        val superResources = resources
        superResources.displayMetrics
        superResources.configuration

        mResources =
            Resources(mAssetManager, superResources.displayMetrics, superResources.configuration)
        mTheme = mResources?.newTheme()
        mTheme?.setTo(super.getTheme())
    }

    /**
     *@desc 添加Disposable方法
     *@author:liuliheng
     *@time: 2020/12/14 15:08
    **/
    protected fun addDisposable(disposable : Disposable) {
        compositeDisposable.addAll(disposable)
    }

    private val compositeDisposable = CompositeDisposable()

    private var mResources : Resources? = null
    private var mAssetManager : AssetManager? = null
    private var mTheme : Resources.Theme? = null

    protected var mDexClassLoader : DexClassLoader? = null

    //apk路径
    private var dexPath : String? = null
    //apk释放目录
    private var fileRelease : File? = null
}