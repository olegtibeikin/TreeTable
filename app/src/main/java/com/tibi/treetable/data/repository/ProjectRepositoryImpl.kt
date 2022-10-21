package com.tibi.treetable.data.repository

import com.tibi.treetable.data.database.TreeTableDao
import com.tibi.treetable.data.database.model.toDatabaseModel
import com.tibi.treetable.data.database.model.toDomainModel
import com.tibi.treetable.domain.project.model.Project
import com.tibi.treetable.domain.project.repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProjectRepositoryImpl(
    private val treeTableDao: TreeTableDao,
) : ProjectRepository {
    override suspend fun addProject(project: Project) =
        withContext(Dispatchers.IO) { treeTableDao.addProject(project.toDatabaseModel()) }

    override suspend fun getProject(id: Int): Project =
        withContext(Dispatchers.IO) { treeTableDao.getProject(id).toDomainModel() }

    override suspend fun updateProject(project: Project) =
        withContext(Dispatchers.IO) { treeTableDao.updateProject(project.toDatabaseModel()) }

    override fun getProjectList(): Flow<List<Project>> =
        treeTableDao.getProjectList().map { list -> list.map { it.toDomainModel() } }
}