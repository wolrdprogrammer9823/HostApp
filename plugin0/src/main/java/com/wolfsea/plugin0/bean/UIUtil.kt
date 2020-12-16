package com.wolfsea.plugin0.bean
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.wolfsea.plugin0.R

/**
 *@desc  UI工具类
 *@author liuliheng
 *@time 2020/12/17  0:30
 **/
class UIUtil {

    //获取Drawable
    fun getDrawable(context: Context) : Drawable? {

        return ResourcesCompat.getDrawable(context.resources, R.drawable.plugin0_bg0, null)
    }
}