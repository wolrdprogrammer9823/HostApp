package com.wolfsea.plugin1

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

/**
 *@desc   UI工具类
 *@author liuliheng
 *@time 2020/12/17  0:29
 **/
class UIUtil {

    //获取Drawable
    fun getDrawable(context: Context) : Drawable? {

        return ResourcesCompat.getDrawable(context.resources, R.drawable.plugin1_bg0, null)
    }
}