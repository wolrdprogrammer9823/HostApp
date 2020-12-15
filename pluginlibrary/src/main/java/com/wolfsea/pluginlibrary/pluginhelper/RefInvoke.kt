package com.wolfsea.pluginlibrary.pluginhelper

/**
 *@desc   反射调用工具类
 *@author liuliheng
 *@time 2020/12/15  18:27
 **/
object RefInvoke {

    /**
     *@desc 创建对象--无参
     *@author:liuliheng
     *@time: 2020/12/15 18:28
    **/
    fun createObject(clazz: Class<*>): Any? {

        val parseTypes = arrayOf<Class<*>?>()
        val parseValues = arrayOf<Any?>()
        try {

            return createObject(clazz, parseTypes, parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc 创建对象--无参
     *@author:liuliheng
     *@time: 2020/12/15 16:59
     **/
    fun createObject(className: String): Any? {

        val parseTypes = arrayOf<Class<*>?>()
        val parseValues = arrayOf<Any?>()
        try {

            val clazz = Class.forName(className)
            return createObject(clazz, parseTypes, parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc 创建对象--一个参数
     *@author:liuliheng
     *@time: 2020/12/15 16:57
     **/
    fun createObject(className: String, parseType: Class<*>?, parseValue: Any?): Any? {

        val parseTypes = arrayOf(parseType)
        val parseValues = arrayOf(parseValue)
        try {

            val clazz = Class.forName(className)
            return createObject(clazz, parseTypes, parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc 创建对象--一个参数
     *@author:liuliheng
     *@time: 2020/12/15 16:51
     **/
    fun createObject(clazz: Class<*>, parseType: Class<*>?, parseValue: Any?): Any? {
        try {

            val parseTypes = arrayOf(parseType)
            val parseValues = arrayOf(parseValue)
            return createObject(clazz, parseTypes, parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc  创建对象--多个参数
     *@author:liuliheng
     *@time: 2020/12/15 16:49
     **/
    fun createObject(className: String, parseTypes: Array<Class<*>?>, parseValues: Array<Any?>): Any? {
        try {

            val clazz = Class.forName(className)
            return createObject(clazz, parseTypes, parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc 创建对象--多个参数
     *@author:liuliheng
     *@time: 2020/12/15 16:42
     **/
    fun createObject(clazz: Class<*>, parseTypes: Array<Class<*>?>, parseValues: Array<Any?>): Any? {

        val constructor = clazz.getDeclaredConstructor(*parseTypes)
        constructor.isAccessible = true
        try {
            return constructor.newInstance(*parseValues)
        } catch (e: Exception) {}

        return null
    }

    /**
     *@desc 调用实例方法--无参
     *@author:liuliheng
     *@time: 2020/12/15 17:24
     **/
    fun invokeInstanceMethod(
        any: Any?,
        methodName: String,
    ): Any? {

        val parseTypes = arrayOf<Class<*>?>()
        val parseValues = arrayOf<Any?>()
        return invokeInstanceMethod(any, methodName, parseTypes, parseValues)
    }

    /**
     *@desc 调用实例方法--一个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:21
     **/
    fun invokeInstanceMethod(
        any: Any?,
        methodName: String,
        parseType: Class<*>?,
        parseValue: Any?
    ): Any? {

        val parseTypes = arrayOf(parseType)
        val parseValues = arrayOf(parseValue)
        return invokeInstanceMethod(any, methodName, parseTypes, parseValues)
    }

    /**
     *@desc 调用实例方法--多个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:20
     **/
    fun invokeInstanceMethod(
        any: Any?,
        methodName: String,
        parseTypes: Array<Class<*>?>,
        parseValues: Array<Any?>
    ): Any? {

        if (any == null) {
            return null
        }

        try {
            //调用一个私有方法
            val method = any.javaClass.getDeclaredMethod(methodName, *parseTypes)
            method.isAccessible = true
            return method.invoke(any, *parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--无参
     *@author:liuliheng
     *@time: 2020/12/15 17:32
     **/
    fun invokeStaticMethod(
        clazzName: String,
        methodName: String,
    ): Any? {

        val parseTypes = arrayOf<Class<*>?>()
        val parseValues = arrayOf<Any?>()

        try {

            val clazz = Class.forName(clazzName)
            return invokeStaticMethod(clazz, methodName, parseTypes, parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--一个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:34
     **/
    fun invokeStaticMethod(
        clazzName: String,
        methodName: String,
        parseType: Class<*>?,
        parseValue: Any?
    ): Any? {

        val parseTypes = arrayOf(parseType)
        val parseValues = arrayOf(parseValue)

        try {

            val clazz = Class.forName(clazzName)
            return invokeStaticMethod(clazz, methodName, parseTypes, parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--多个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:34
     **/
    fun invokeStaticMethod(
        clazzName: String,
        methodName: String,
        parseTypes: Array<Class<*>?>,
        parseValues: Array<Any?>
    ): Any? {
        try {

            val clazz = Class.forName(clazzName)
            return invokeStaticMethod(clazz, methodName, parseTypes, parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--无参
     *@author:liuliheng
     *@time: 2020/12/15 17:32
     **/
    fun invokeStaticMethod(
        clazz: Class<*>,
        methodName: String,
    ): Any? {

        val parseTypes = arrayOf<Class<*>?>()
        val parseValues = arrayOf<Any?>()
        try {

            return invokeStaticMethod(clazz, methodName, parseTypes, parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--一个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:30
     **/
    fun invokeStaticMethod(
        clazz: Class<*>,
        methodName: String,
        parseType: Class<*>?,
        parseValue: Any?
    ): Any? {
        try {

            val parseTypes = arrayOf(parseType)
            val parseValues = arrayOf(parseValue)
            return invokeStaticMethod(clazz, methodName, parseTypes, parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 调用静态方法--多个参数
     *@author:liuliheng
     *@time: 2020/12/15 17:29
     **/
    fun invokeStaticMethod(
        clazz: Class<*>,
        methodName: String,
        parseTypes: Array<Class<*>?>,
        parseValues: Array<Any?>
    ): Any? {
        try {
            //调用一个私有方法
            val method = clazz.getDeclaredMethod(methodName, *parseTypes)
            method.isAccessible = true
            return method.invoke(null, *parseValues)
        } catch (e :Exception) {}

        return null
    }

    /**
     *@desc 获取实例字段
     *@author:liuliheng
     *@time: 2020/12/15 18:40
    **/
    fun getFieldObject(any: Any, filedName: String): Any? {
        try {

            return getFieldObject(any.javaClass, any, filedName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *@desc 获取实例字段
     *@author:liuliheng
     *@time: 2020/12/15 18:39
    **/
    fun getFieldObject(clazzName: String, any: Any?, filedName: String): Any? {
        try {

            val clazz = Class.forName(clazzName)
            return getFieldObject(clazz, any, filedName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *@desc 获取实例字段
     *@author:liuliheng
     *@time: 2020/12/15 18:33
     **/
    fun getFieldObject(clazz: Class<*>, any: Any?, filedName: String): Any? {
        try {

            val filed = clazz.getDeclaredField(filedName)
            filed.isAccessible = true
            return filed.get(any)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *@desc 设置实例字段值
     *@author:liuliheng
     *@time: 2020/12/15 18:44
    **/
    fun setFieldObject(any: Any, filedName: String, filedValue: Any?) {
        try {

            setFieldObject(any.javaClass, any, filedName, filedValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *@desc 设置实例字段值
     *@author:liuliheng
     *@time: 2020/12/15 18:43
    **/
    fun setFieldObject(clazzName: String, any: Any?, filedName: String, filedValue: Any?) {
        try {

            val clazz = Class.forName(clazzName)
            setFieldObject(clazz, any, filedName, filedValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *@desc 设置实例字段值
     *@author:liuliheng
     *@time: 2020/12/15 18:43
    **/
    fun setFieldObject(clazz: Class<*>, any: Any?, filedName: String, filedValue: Any?) {
        try {

            val filed = clazz.getDeclaredField(filedName)
            filed.isAccessible = true
            filed.set(any, filedValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *@desc 获取静态字段对象
     *@author:liuliheng
     *@time: 2020/12/15 18:48
    **/
    fun getStaticFiledObject(clazzName: String, filedName: String): Any? {
        try {

            val clazz = Class.forName(clazzName)
            return getFieldObject(clazz, null, filedName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *@desc 获取静态字段对象
     *@author:liuliheng
     *@time: 2020/12/15 18:47
    **/
    fun getStaticFiledObject(clazz: Class<*>, filedName: String): Any? {
        try {

            getFieldObject(clazz, null, filedName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *@desc 设置静态字段
     *@author:liuliheng
     *@time: 2020/12/15 18:55
    **/
    fun setStaticFiledObject(clazzName: String, filedName: String, filedValue: Any?) {
        try {

            val clazz = Class.forName(clazzName)
            setFieldObject(clazz, null, filedName, filedValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *@desc 设置静态字段
     *@author:liuliheng
     *@time: 2020/12/15 18:54
    **/
    fun setStaticFiledObject(clazz: Class<*>, filedName: String, filedValue: Any?) {
        try {

            setFieldObject(clazz, null, filedName, filedValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}