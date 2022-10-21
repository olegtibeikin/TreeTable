package com.tibi.treetable.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tibi.treetable.data.database.model.ProjectEntity
import com.tibi.treetable.data.database.model.TreeConverter
import com.tibi.treetable.data.database.model.TreeEntity

@Database(entities = [ProjectEntity::class, TreeEntity::class], version = 2, exportSchema = false)
@TypeConverters(TreeConverter::class)
abstract class TreeTableDatabase : RoomDatabase() {
    abstract fun treeTableDao(): TreeTableDao
}