package com.tibi.treetable.screens.project.projectAdd

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tibi.treetable.domain.project.model.Project
import com.tibi.treetable.domain.project.usecase.AddProjectUseCase
import kotlinx.coroutines.launch

class ProjectAddViewModel(
    private val addProjectUseCase: AddProjectUseCase
) : ViewModel() {
    var projectName = mutableStateOf("")
        private set

    fun onAddProject(onOpenTable: (projectId: Long) -> Unit) {
        if (projectName.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val projectId = addProjectUseCase.execute(
                        Project(
                            name = projectName.value,
                            date = System.currentTimeMillis()
                        )
                    )
                    onOpenTable(projectId)
                } catch (e: Throwable) {
                    Log.e("ProjectAddViewModel", "onAddProject: ${e.message}")
                }
            }
        }
    }
}