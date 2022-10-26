package com.tibi.treetable.data.database

import androidx.room.*
import com.tibi.treetable.data.database.model.ProjectEntity
import com.tibi.treetable.data.database.model.TreeEntity
import com.tibi.treetable.domain.table.model.TreeType
import kotlinx.coroutines.flow.Flow

@Dao
interface TreeTableDao {
    // Project
    @Query("SELECT * FROM projects_tbl")
    fun getProjectList(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects_tbl WHERE id = :id")
    suspend fun getProject(id: Int): ProjectEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProject(project: ProjectEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProject(project: ProjectEntity)

    //Tree
    @Query("SELECT * FROM trees_tbl WHERE projectId = :projectId")
    fun getTreeList(projectId: Int): Flow<List<TreeEntity>>

    @Query("SELECT type FROM trees_tbl WHERE projectId = :projectId GROUP BY type ORDER BY COUNT(*)")
    fun getSortedTreeTypeList(projectId: Int): Flow<List<TreeType>>

    @Query("SELECT * FROM trees_tbl WHERE id = :id")
    suspend fun getTree(id: Int): TreeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTree(tree: TreeEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTree(tree: TreeEntity)

    @Delete
    suspend fun deleteTree(tree: TreeEntity)
}