package com.wolfsea.pluginhelper
import dalvik.system.DexClassLoader

/**
 *@desc   插件信息实体类
 *@author liuliheng
 *@time 2020/12/16  22:11
 **/
class PluginInfo(val dexPath: String, val dexClassLoader: DexClassLoader)
