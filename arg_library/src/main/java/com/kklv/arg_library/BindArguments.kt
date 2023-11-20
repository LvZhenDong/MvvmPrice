package com.kklv.arg_library

import android.app.Activity
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author lvzhendong
 * @data 2023/11/20
 * @description
 */
object BindArguments {
    fun bind(activity: Activity, dataManager: Any) {
        val clazz: Class<*> = dataManager.javaClass
        try {
            val bindViewClass: Class<*> = Class.forName(clazz.name + "_ArgumentsBinding")
            val method: Method = bindViewClass.getMethod("bindArguments", Class.forName("android.app.Activity"), clazz)
            method.invoke(bindViewClass.newInstance(), activity, dataManager)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }
}