package cn.vove7.android.scaffold.demo

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import cn.vove7.android.common.Logger
import cn.vove7.android.scaffold.app.ScaffoldApp
import cn.vove7.android.scaffold.demo.app.AppDatabase
import cn.vove7.android.scaffold.demo.app.appModules
import cn.vove7.android.scaffold.demo.data.Config
import cn.vove7.android.scaffold.ui.base.ScaffoldActivity
import glimpse.core.Glimpse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * # App
 * Created on 2019/11/25
 *
 * @author Vove
 */
class App : ScaffoldApp() {
    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            for (i in 0..9)
                AppDatabase.configdDao.insert(Config("key_$i", "value_%i"))
        }

        startKoin {
            androidContext(this@App)
            modules(appModules)
        }

        Glimpse.init(this)

        Logger {
            outputLevel = if(BuildConfig.DEBUG) Log.VERBOSE else 100
            callstackDepth = 3
        }
        ScaffoldActivity.apply {
            enableAutoDarkTheme = true
            globalDarkTheme = R.style.DarkTheme
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
