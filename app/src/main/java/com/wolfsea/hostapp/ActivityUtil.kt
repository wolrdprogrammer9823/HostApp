package com.wolfsea.hostapp
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 *@desc  Activity相关的工具类
 *@author liuliheng
 *@time 2020/12/14  22:36
 **/

fun gotoActivity(context: Context, clazz: Class<out AppCompatActivity>) {

    val intent = Intent(context, clazz)
    context.startActivity(intent)
}