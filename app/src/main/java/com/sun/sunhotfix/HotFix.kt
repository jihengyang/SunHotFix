package com.sun.sunhotfix

import android.annotation.SuppressLint
import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File

/**
 * @author hengyangji
 * on 2021/11/8
 */
object HotFix {

    @SuppressLint("DiscouragedPrivateApi")
    fun doHotFix(context: Context) {
        val pathList = Class.forName("dalvik.system.BaseDexClassLoader").getDeclaredField("pathList").run {
            isAccessible = true
            get(context.classLoader)
        }
        val dexElementsField = pathList.javaClass.getDeclaredField("dexElements")
        val currentElementList = dexElementsField.run {
            isAccessible = true
            get(pathList)
        } as? Array<*>

        val fixedElementList = attainFixedElementList(context)

        dexElementsField.set(pathList, Utils.mergeArray(fixedElementList, currentElementList))
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun attainFixedElementList(context: Context): Array<*>? {
        val fixDexFile = attainFixedDexFile(context)
        val dexOptPath = context.getDir("dexopt", 0).absolutePath
        val dexClassLoader = DexClassLoader(fixDexFile.absolutePath, dexOptPath, dexOptPath, context.classLoader)
        val pathList = Class.forName("dalvik.system.BaseDexClassLoader").getDeclaredField("pathList").run {
            isAccessible = true
            get(dexClassLoader)
        }
        return pathList.javaClass.getDeclaredField("dexElements").run {
            isAccessible = true
            get(pathList)
        } as? Array<*>
    }

    private fun attainFixedDexFile(context: Context): File {
        return File(context.filesDir.absolutePath, "fix_dex.jar").apply {
            writeBytes(context.assets.open("fix_dex.jar").readBytes())
        }
    }

}