package com.tibi.treetable.di.data

import android.app.Application
import androidx.room.Room
import com.tibi.treetable.data.database.TreeTableDao
import com.tibi.treetable.data.database.TreeTableDatabase
import com.tibi.treetable.data.repository.ProjectRepositoryImpl
import com.tibi.treetable.data.repository.TreeRepositoryImpl
import com.tibi.treetable.domain.project.repository.ProjectRepository
import com.tibi.treetable.domain.table.repository.TreeRepository
import org.koin.dsl.module

val dataModule = module {
    fun provideDatabase(application: Application): TreeTableDatabase {
        return Room.databaseBuilder(application, TreeTableDatabase::class.java, "tree_table_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: TreeTableDatabase): TreeTableDao {
        return database.treeTableDao()
    }

    single { provideDatabase(get()) }
    single { provideDao(get()) }

    // Project
    single<ProjectRepository> {
        ProjectRepositoryImpl(
            treeTableDao = get()
        )
    }

    // Tree
    single<TreeRepository> {
        TreeRepositoryImpl(
            treeTableDao = get()
        )
    }
}