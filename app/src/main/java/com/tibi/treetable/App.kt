package com.tibi.treetable

import android.app.Application
import com.tibi.treetable.di.data.dataModule
import com.tibi.treetable.di.domain.domainProjectModule
import com.tibi.treetable.di.domain.domainTreeModule
import com.tibi.treetable.di.presentation.projectAddModule
import com.tibi.treetable.di.presentation.projectListModule
import com.tibi.treetable.di.presentation.treeModule
import com.tibi.treetable.di.presentation.treeTableModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    projectListModule,
                    domainProjectModule,
                    dataModule,
                    domainTreeModule,
                    projectAddModule,
                    treeTableModule,
                    treeModule
                )
            )
        }
    }
}