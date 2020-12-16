package com.wolfsea.plugin0.bean
import android.content.Context
import com.wolfsea.plugin0.R
import com.wolfsea.pluginlibrary.IDynamic

/**
 *@desc
 *@author liuliheng
 *@time 2020/12/16  17:47
 **/
class Dynamic : IDynamic {

    override fun getStringForResId(context: Context?) : String = context?.resources?.getString(R.string.load_plugin_resource)!!
}