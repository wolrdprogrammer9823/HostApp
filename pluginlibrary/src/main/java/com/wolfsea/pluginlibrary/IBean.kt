package com.wolfsea.pluginlibrary

/**
 *@desc  传递实体数据接口
 *@author liuliheng
 *@time 2020/12/15  9:31
 **/
interface IBean {

    fun getName(): String

    fun setName(name: String)

    fun registerCallback(callBack: ICallBack)
}