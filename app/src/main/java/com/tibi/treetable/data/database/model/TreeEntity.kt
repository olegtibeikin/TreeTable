package com.tibi.treetable.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.model.TreeType

@Entity(tableName = "trees_tbl")
data class TreeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val projectId: Int,
    val number: Int,
    val type: TreeType,
    val diameters: List<Int>,
    val note: String
)

fun TreeEntity.toDomainModel() =
    Tree(
        id = this.id,
        projectId = this.projectId,
        number = this.number,
        type = this.type,
        diameters = this.diameters,
        note = this.note
    )

fun Tree.toDatabaseModel() =
    TreeEntity(
        id = this.id,
        projectId = this.projectId,
        number = this.number,
        type = this.type,
        diameters = this.diameters,
        note = this.note
    )