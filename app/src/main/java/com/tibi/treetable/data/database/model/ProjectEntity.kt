package com.tibi.treetable.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tibi.treetable.domain.project.model.Project

@Entity(tableName = "projects_tbl")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "project_name")
    val name: String,
    val date: Long
)

fun ProjectEntity.toDomainModel() =
    Project(
        id = this.id,
        name = this.name,
        date = this.date
    )

fun Project.toDatabaseModel() =
    ProjectEntity(
        id = this.id,
        name = this.name,
        date = this.date
    )