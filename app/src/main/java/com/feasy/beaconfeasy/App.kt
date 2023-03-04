package com.feasy.beaconfeasy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.feasy.beaconfeasy.utils.getStr
import com.feasy.beaconfeasy.utils.putStr
import com.feasycom.feasybeacon.logic.model.BeaconParameter
import com.feasycom.feasybeacon.logic.model.Devices
import com.feasycom.feasybeacon.logic.network.BeaconNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

const val UNKNOWN_DEVICE = 0
const val ORDINARY_DEVICE = 1
const val BEACON_DEVICE = 2

const val ALL_FUNCTIONS = 0

class App: Application()  {

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext

        val data = getStr("hash", "")


        // Crash 捕捉界面
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)
            .trackActivities(true)
            .minTimeBetweenCrashesMs(2000) // 重启的 Activity
            .restartActivity(MainActivity::class.java) // 错误的 Activit
            //.eventListener(new YourCustomEventListener())
            .apply()

    }

    private fun Context.assetsToJsonString(fileName: String): String{
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //通过管理器打开文件并读取
            val bf = BufferedReader(
                InputStreamReader(
                    assets.open(fileName)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    companion object{

        @SuppressLint("StaticFieldLeak")
        lateinit var sContext: Context

      /*  val mBeaconItemDao by lazy {
            DeviceDatabase.getDataBase(sContext).deviceDao()
        }

       */
    }
}