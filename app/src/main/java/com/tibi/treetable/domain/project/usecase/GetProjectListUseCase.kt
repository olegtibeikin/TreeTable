package com.tibi.treetable.domain.project.usecase

import com.tibi.treetable.domain.project.repository.ProjectRepository

class GetProjectListUseCase(
    private val projectRepository: ProjectRepository,
) {
    fun execute() = projectRepository.getProjectList()
}