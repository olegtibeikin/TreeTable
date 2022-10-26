package com.tibi.treetable.screens.tree

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tibi.treetable.domain.table.model.Tree
import com.tibi.treetable.domain.table.model.TreeType
import com.tibi.treetable.domain.table.usecase.AddTreeUseCase
import com.tibi.treetable.domain.table.usecase.GetSortedTreeTypeListUseCase
import com.tibi.treetable.screens.project.projectList.ProjectListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val NOT_SELECTED = -1

class TreeViewModel(
    private val projectId: Long,
    private val treeId: Long,
    private val addTreeUseCase: AddTreeUseCase,
    private val getSortedTreeTypeListUseCase: GetSortedTreeTypeListUseCase
) : ViewModel() {
    private val _treeTypeList = MutableStateFlow(listOf<TreeType>())
    val treeTypeList = _treeTypeList.asStateFlow()

    private val _topTreeTypeList = MutableStateFlow(listOf<TreeType>())
    val topTreeTypeList = _topTreeTypeList.asStateFlow()

    var treeNumber = mutableStateOf("")
        private set

    var selectedTreeType by mutableStateOf(TreeType.STATION)
        private set

    var diameters by mutableStateOf(listOf(20))
        private set

    var selectedDiameterIndex by mutableStateOf(NOT_SELECTED)
        private set

    init {
        viewModelScope.launch {
            getSortedTreeTypeListUseCase.execute(projectId.toInt()).distinctUntilChanged()
                .collect { list ->
                    val newList = TreeType.values().toList() - list.toSet() + list
                    val lastIndex = newList.lastIndex
                    val topIndex = lastIndex - 5
                    _topTreeTypeList.value = newList.slice(topIndex..lastIndex)
                    _treeTypeList.value = newList.slice(0 until topIndex)
                }
        }
    }

    fun onSelectTreeType(treeType: TreeType) {
        selectedTreeType = treeType
    }

    fun addDiameter(diameter: Int): Int {
        diameters = diameters.toMutableList() + diameter
        return diameters.lastIndex
    }

    fun onDiameterSelected(index: Int) {
        selectedDiameterIndex = index
    }

    fun onResetSelectedDiameter() {
        selectedDiameterIndex = NOT_SELECTED
    }

    fun addTree() {
        viewModelScope.launch {
            addTreeUseCase.execute(
                Tree(
                    projectId = projectId.toInt(),
                    number = 0,
                    type = selectedTreeType,
                    diameters = diameters,
                    note = ""
                )
            )
        }
    }
}

