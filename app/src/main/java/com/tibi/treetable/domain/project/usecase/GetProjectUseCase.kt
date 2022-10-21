package com.tibi.treetable.domain.project.usecase

import com.tibi.treetable.domain.project.repository.ProjectRepository

class GetProjectUseCase(
    private val projectRepository: ProjectRepository,
) {
    suspend fun execute(id: Int) = projectRepository.getProject(id)
}