package com.wolfsea.plugin0.bean
import com.wolfsea.pluginlibrary.IBean
import com.wolfsea.pluginlibrary.ICallBack

/**
 *@desc  实体类
 *@author liuliheng
 *@time 2020/12/14  18:32
 **/
class FirstBean : IBean {

    var beanName = "beanName000"

    private var mCallback : ICallBack? = null

    override fun getName(): String = beanName

    override fun setName(name: String) {
        beanName = name
    }

    override fun registerCallback(callBack: ICallBack) {
        mCallback = callBack
        clickToButton()
    }

    public fun clickToButton() {
        mCallback?.setResult(beanName)
    }
}