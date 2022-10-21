package com.tibi.treetable.domain.project.repository

import com.tibi.treetable.domain.project.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun addProject(project: Project): Long
    suspend fun getProject(id: Int): Project?
    suspend fun updateProject(project: Project)
    fun getProjectList(): Flow<List<Project>>
}