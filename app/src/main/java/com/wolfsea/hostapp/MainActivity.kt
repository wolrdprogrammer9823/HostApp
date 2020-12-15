package com.wolfsea.hostapp
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wolfsea.pluginlibrary.IBean
import com.wolfsea.pluginlibrary.ICallBack
import com.wolfsea.pluginlibrary.pluginhelper.PluginResourcesExtractUtil
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

const val SOURCE_NAME = "plugin0-debug.apk"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mDexClassLoader: DexClassLoader? = null

    private var dexPath: String? = null
    private var fileRelease: File? = null

    override fun attachBaseContext(newBase: Context?) {

        super.attachBaseContext(newBase)
        try {
            PluginResourcesExtractUtil.extractAssets(newBase, SOURCE_NAME)
        } catch (e: Exception) {
        }
    }

    override fun onContentChanged() {

        super.onContentChanged()
        test_plugin_btn.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extractFile = this.getFileStreamPath(SOURCE_NAME)
        dexPath = extractFile.path
        fileRelease = this.getDir("dex", Context.MODE_PRIVATE)

        mDexClassLoader = DexClassLoader(dexPath, fileRelease!!.absolutePath, null, classLoader)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.test_plugin_btn -> {

                val classBean: Class<out Any>?
                try {

                    classBean = mDexClassLoader?.loadClass("com.wolfsea.plugin0.bean.FirstBean")

                    //普通反射方式
//                    val any: Any? = classBean?.newInstance()
//                    val method = any?.javaClass?.getMethod("getBeanName")
//                    method?.isAccessible = true
//                    val name: String = method?.invoke(any) as String
//                    doLog("name:$name")

                    //面向接口方式
                    val bean: IBean = classBean?.newInstance() as IBean

                    doLog("name:${bean.getName()}")
                    bean.setName("插件化编程")
                    doLog("name:${bean.getName()}")

                    //回调  主动获取数据
                    val callback : ICallBack = object : ICallBack {
                       override fun setResult(result: String) {

                           doLog("name:$result")
                       }
                    }

                    bean.registerCallback(callback)
                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }
            else -> {}
        }
    }
}