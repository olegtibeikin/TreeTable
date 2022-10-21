package com.tibi.treetable.di.domain

import com.tibi.treetable.domain.project.usecase.AddProjectUseCase
import com.tibi.treetable.domain.project.usecase.GetProjectListUseCase
import com.tibi.treetable.domain.project.usecase.GetProjectUseCase
import com.tibi.treetable.domain.project.usecase.UpdateProjectUseCase
import org.koin.dsl.module

val domainProjectModule = module {
    factory {
        GetProjectUseCase(projectRepository = get())
    }
    factory {
        GetProjectListUseCase(projectRepository = get())
    }
    factory {
        AddProjectUseCase(projectRepository = get())
    }
    factory {
        UpdateProjectUseCase(projectRepository = get())
    }
}