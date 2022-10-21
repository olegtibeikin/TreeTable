package com.tibi.treetable.domain.project.usecase

import com.tibi.treetable.domain.project.model.Project
import com.tibi.treetable.domain.project.repository.ProjectRepository

class AddProjectUseCase(
    private val projectRepository: ProjectRepository,
) {

    suspend fun execute(project: Project) = projectRepository.addProject(project)
}