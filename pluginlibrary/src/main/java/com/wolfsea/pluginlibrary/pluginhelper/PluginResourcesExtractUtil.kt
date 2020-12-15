package com.wolfsea.pluginlibrary.pluginhelper
import android.content.Context
import android.content.res.AssetManager
import java.io.*
import java.lang.RuntimeException

/**
 *@desc  插件资源抽取工具
 *@author liuliheng
 *@time 2020/12/15  22:47
 **/
object PluginResourcesExtractUtil {

    //基本目录
    private var sBaseDir : File? = null

    /**
     *@desc 把Assets里面的资源复制到/data/data/file目录下
     *@author:liuliheng
     *@time: 2020/12/14 18:53
     **/
    fun extractAssets(context: Context?, sourceName: String) {

        val  assetManager : AssetManager = context!!.assets
        var inputStream : InputStream? = null
        var outputStream : FileOutputStream? = null

        try {

            inputStream = assetManager.open(sourceName)
            val extractFile = context.getFileStreamPath(sourceName)
            outputStream = FileOutputStream(extractFile)
            val byteArray = ByteArray(1024)
            var count: Int
            while ((inputStream.read(byteArray).also { count = it }) > 0) {

                outputStream.write(byteArray, 0, count)
            }

            outputStream.flush()
        } catch (e: IOException) {

            e.printStackTrace()
        } finally {

            closeSilently(outputStream)
            closeSilently(inputStream)
        }
    }

    /**
     *@desc 待插件经过opt优化后存放odex的路径
     *@author:liuliheng
     *@time: 2020/12/14 22:00
     **/
    fun getPluginOptDexDir(context: Context,packageName: String) : File {
        return enforceDirExits(File(getPluginBaseDir(context, packageName), "odex"))
    }

    /**
     *@desc 获取插件所在lib的目录
     *@author:liuliheng
     *@time: 2020/12/14 21:58
     **/
    fun getPluginLibDir(context: Context,packageName: String): File {
        return enforceDirExits(File(getPluginBaseDir(context, packageName), "lib"))
    }

    /**
     *@desc  获得插件的基本目录
     *@author:liuliheng
     *@time: 2020/12/14 21:48
     **/
    private fun getPluginBaseDir(context: Context,packageName: String): File {
        if (sBaseDir == null) {

            sBaseDir = context.getFileStreamPath(packageName)
            enforceDirExits(sBaseDir!!)
        }
        return enforceDirExits(File(sBaseDir, packageName))
    }

    /**
     *@desc 强制生成指定目录
     *@author:liuliheng
     *@time: 2020/12/14 21:54
     **/
    @Synchronized
    private fun enforceDirExits(baseDir: File): File {
        if (!baseDir.exists()) {

            val result = baseDir.mkdirs()
            if (!result) {

                throw RuntimeException("created dir $baseDir failed")
            }
        }

        return baseDir
    }

    /**
     *@desc 关闭资源
     *@author:liuliheng
     *@time: 2020/12/14 18:51
     **/
    private fun closeSilently(closeable : Closeable?) {
        try {
            closeable?.close()
        } catch (e: Exception) {}
    }
}