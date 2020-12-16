package com.wolfsea.hostapp
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wolfsea.pluginhelper.PluginManager
import com.wolfsea.pluginhelper.RefInvoke
import com.wolfsea.pluginlibrary.IBean
import com.wolfsea.pluginlibrary.ICallBack
import com.wolfsea.pluginlibrary.IDynamic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onContentChanged() {

        super.onContentChanged()

        test_plugin_btn.setOnClickListener(this)
        load_plugin_resource_btn.setOnClickListener(this)

        load_plugin0_skin_btn.setOnClickListener(this)
        load_plugin1_skin_btn.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.test_plugin_btn -> {
                //加载插件中的数据
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

            R.id.load_plugin_resource_btn -> {

                //加载资源
                loadResources()

                //加载插件中的资源
                val clazzObject: Class<*>?
                try {

                    clazzObject = mDexClassLoader?.loadClass("com.wolfsea.plugin0.bean.Dynamic")
                    val dynamic: IDynamic = clazzObject?.newInstance() as IDynamic
                    doLog("resources:${dynamic.getStringForResId(this@MainActivity)}")

                    Toast.makeText(
                        this@MainActivity.applicationContext,
                        "获取的插件中的资源为:${dynamic.getStringForResId(this@MainActivity)}",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {}
            }

            R.id.load_plugin0_skin_btn -> {

                //加载插件0皮肤
                val pluginInfo = PluginManager.pluginsInfo[SOURCE_NAME0]
                loadResources(pluginInfo!!.dexPath)
                val drawable : Drawable = loadPluginSkin(pluginInfo.dexClassLoader,"com.wolfsea.plugin0.bean.UIUtil")
                root_layout.background = drawable

            }

            R.id.load_plugin1_skin_btn -> {
                //加载插件1皮肤
                val pluginInfo = PluginManager.pluginsInfo[SOURCE_NAME1]
                loadResources(pluginInfo!!.dexPath)
                val drawable : Drawable = loadPluginSkin(pluginInfo.dexClassLoader,"com.wolfsea.plugin1.UIUtil")
                root_layout.background = drawable
            }
            else -> {}
        }
    }

    /**
     *@desc 加载插件中的皮肤
     *@author:liuliheng
     *@time: 2020/12/16 23:14
    **/
    private fun loadPluginSkin(classLoader: ClassLoader, clazzName: String): Drawable {
        val clazz : Class<*> = classLoader.loadClass(clazzName)
        return RefInvoke.invokeInstanceMethod(clazz, "getDrawable", Context::class.java, this)!! as Drawable
    }

}