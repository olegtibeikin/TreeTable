package com.tibi.treetable.di.presentation

import com.tibi.treetable.screens.project.projectAdd.ProjectAddViewModel
import com.tibi.treetable.screens.project.projectList.ProjectListViewModel
import com.tibi.treetable.screens.table.TableViewModel
import com.tibi.treetable.screens.tree.TreeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectListModule = module {
    viewModel {
        ProjectListViewModel(
            getProjectListUseCase = get(),
            updateProjectUseCase = get()
        )
    }
}

val projectAddModule = module {
    viewModel {
        ProjectAddViewModel(
            addProjectUseCase = get()
        )
    }
}

val treeTableModule = module {
    viewModel { parameters ->
        TableViewModel(
            projectId = parameters.get(),
            getTreeListUseCase = get()
        )
    }
}

val treeModule = module {
    viewModel { parameters ->
        TreeViewModel(
            projectId = parameters.get(),
            treeId = parameters.get()
        )
    }
}