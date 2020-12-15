package com.wolfsea.pluginlibrary.pluginhelper
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader

/**
 *@desc  容器ClassLoader
 *@author liuliheng
 *@time 2020/12/15  17:41
 **/
class ZeusClassLoader(dexPath: String?, parent: ClassLoader?) : PathClassLoader(dexPath, parent) {

    private val mClassLoaderList: MutableList<DexClassLoader>

    init {
        mClassLoaderList = ArrayList()
    }

    /**
     *@desc 添加一个插件到当前的ClassLoader中
     *@author:liuliheng
     *@time: 2020/12/15 17:53
    **/
    fun addPluginClassLoader(dexClassLoader: DexClassLoader) {
        mClassLoaderList.add(dexClassLoader)
    }


    @Throws(ClassNotFoundException::class)
    override fun loadClass(clazzName: String?, resolve: Boolean): Class<*> {

        var clazz: Class<*>? = null
        try {

            //先从parent ClassLoader查找,这是系统创建的,对应宿主的apk
            clazz = parent.loadClass(clazzName)
        } catch (e: ClassNotFoundException) {

            e.printStackTrace()
        }

        if (clazz != null) {
            return clazz
        }

        for (classLoader in mClassLoaderList) {

            clazz = classLoader.loadClass(clazzName)
            if (clazz != null) {

                return clazz
            }
        }

        throw ClassNotFoundException("$clazzName in loader $this")
    }
}