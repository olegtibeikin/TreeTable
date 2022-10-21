package com.tibi.treetable.screens.project.projectList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tibi.treetable.domain.project.model.Project
import com.tibi.treetable.domain.project.usecase.GetProjectListUseCase
import com.tibi.treetable.domain.project.usecase.UpdateProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProjectListViewModel(
    private val getProjectListUseCase: GetProjectListUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase
) : ViewModel() {
    private val _projectListState = MutableStateFlow<ProjectListState>(ProjectListState.Loading)
    val projectListState = _projectListState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getProjectListUseCase.execute().distinctUntilChanged()
                    .collect { list ->
                        _projectListState.value = ProjectListState.Success(
                            list.sortedByDescending { it.date }
                        )
                    }
            } catch (e: Throwable) {
                _projectListState.value = ProjectListState.Error(e)
            }
        }
    }

    fun updateProjectDate(project: Project) {
        viewModelScope.launch {
            try {
                updateProjectUseCase.execute(
                    project.copy(
                        date = System.currentTimeMillis()
                    )
                )
            } catch (e: Throwable) {

            }
        }
    }
}

sealed interface ProjectListState {
    object Loading : ProjectListState
    data class Success(val projectList: List<Project>) : ProjectListState
    data class Error(val e: Throwable) : ProjectListState
}