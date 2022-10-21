package com.tibi.treetable.domain.table.repository

import com.tibi.treetable.domain.table.model.Tree
import kotlinx.coroutines.flow.Flow

interface TreeRepository {
    suspend fun addTree(tree: Tree): Long
    suspend fun getTree(id: Int): Tree
    suspend fun updateTree(tree: Tree)
    suspend fun deleteTree(tree: Tree)
    fun getTreeList(projectId: Int): Flow<List<Tree>>
}