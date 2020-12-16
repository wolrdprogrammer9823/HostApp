package com.wolfsea.pluginlibrary
import android.content.Context

/**
 *@desc   动态加载资源接口
 *@author liuliheng
 *@time 2020/12/16  17:41
 **/
interface IDynamic {

    fun getStringForResId(context: Context?): String
}