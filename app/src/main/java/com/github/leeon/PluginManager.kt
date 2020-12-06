package com.github.leeon

import android.app.Application
import com.github.leeon.utils.FileUtils
import dalvik.system.DexClassLoader
import java.io.File

/**
 * Created by Lyongwang on 2020/11/28 15: 37.
 *
 * Email: liyongwang@yiche.com
 */
object PluginManager {
    private const val plugin_path = "plg"
    private const val plugin_name = "plugin.png"

    fun loadAllPlugins(app: Application): DexClassLoader{
        val dest_path = app.filesDir.path + File.separator + plugin_path
        var dest_file_path = dest_path + File.separator + plugin_name
        FileUtils.copyFileFromAssets(app, plugin_name , dest_file_path)
        return DexClassLoader(dest_file_path, dest_path, "", null)
    }
}