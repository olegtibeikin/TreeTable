package com.tibi.treetable.data.repository

import com.tibi.treetable.data.database.TreeTableDao
import com.tibi.treetable.data.database.model.toDatabaseModel
import com.tibi.treetable.data.database.model.toDomainModel
import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.repository.TreeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TreeRepositoryImpl(
    private val treeTableDao: TreeTableDao,
) : TreeRepository {
    override suspend fun addTree(tree: Tree) =
        treeTableDao.addTree(tree.toDatabaseModel())

    override suspend fun getTree(id: Int) =
        treeTableDao.getTree(id).toDomainModel()

    override suspend fun updateTree(tree: Tree) =
        treeTableDao.updateTree(tree.toDatabaseModel())

    override suspend fun deleteTree(tree: Tree) =
        treeTableDao.deleteTree(tree.toDatabaseModel())

    override fun getTreeList(projectId: Int): Flow<List<Tree>> =
        treeTableDao.getTreeList(projectId).map { list -> list.map { it.toDomainModel() } }

}